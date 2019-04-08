/*
 * Copyright (c) 2018.
 * Author: Mikhail Sigachev
 */

package com.zource.controllers.admin.brands;


import com.zource.controllers.admin.BrandFormValidator;
import com.zource.dao.BrandDAO;
import com.zource.entity.Brands;
import com.zource.form.BrandForm;
import com.zource.model.Info;
import com.zource.model.notifications.Notification;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@Controller
@Transactional
public class AdminBrandsController {

    //Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = "/images/brands/";

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private BrandDAO brandDAO;

    @Autowired
    Environment env;

    @Autowired
    private BrandFormValidator brandFormValidator;

    @InitBinder
    public void myInitBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }
        System.out.println("Target=" + target);

        if (target.getClass() == BrandForm.class) {
            dataBinder.addValidators(brandFormValidator);   //add or set
        }
    }


    // List all brands
    @RequestMapping("/admin/brands")
    public String brandsList(@RequestParam(value = "search", defaultValue = "", required = false) String search,
                             @ModelAttribute("info") Info info,
                             Model model) {


        List<Brands> brands = brandDAO.getAllBrands();


        info.getBreadcrumb().put("Brands", "/admin/brands");
        info.getBreadcrumb().inverse();
        info.setTitle("Brands list");
        model.addAttribute("brands", brands);

        return "admin/brands/list";
    }


    // GET: new brand
    @RequestMapping(value = {"/admin/brand"}, method = RequestMethod.GET)
    public String brand(Model model, @RequestParam(value = "id", defaultValue = "0", required = false) Integer id,
                        @ModelAttribute("info") Info info) {

        BrandForm brandForm = null;


        if (id instanceof Integer) {
            Brands brand = brandDAO.getBrandById(id);
            if (brand != null) {
                brandForm = new BrandForm(brand);
            }
        }
        if (brandForm == null) {
            brandForm = new BrandForm();
            brandForm.setNewBrand(true);
        }

        info.setTitle("Brand Info");
        info.getBreadcrumb().put("Brands", "/admin/brands");
        info.getBreadcrumb().put(brandForm.getName(), "");
        info.getBreadcrumb().inverse();
        model.addAttribute("brandForm", brandForm);

        return "admin/brands/brand";
    }


    // POST: Save brand
    @RequestMapping(value = {"/admin/brand"}, method = RequestMethod.POST)
    public String brandSave(Model model, //
                            @ModelAttribute("brandForm") @Validated BrandForm brandForm,
                            BindingResult result,
                            @ModelAttribute("info") Info info,
                            final RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            System.out.println("##ERRORS");
            return "admin/brands/brand";
        }

        try {
            System.out.println("##before save ");
            brandDAO.save(brandForm);
        } catch (Exception e) {
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            String message = ExceptionUtils.getStackTrace(e);
            model.addAttribute("errorMessage", message);
            // Show product form.
            return "admin/brands/brand";
        }

        redirectAttributes.addFlashAttribute("notification", new Notification("Category updated!").success());
        return "redirect:/admin/brands";
    }

    // POST: Do Upload
    @PostMapping("/uploadBrandLogo")
    public String uploadBrandLogoPOST(HttpServletRequest request, //
                                      Model model, //
                                      @ModelAttribute("brandForm") BrandForm brandForm, RedirectAttributes  redirectAttributes) {


        return this.doUploadBrandLogo(request, model, brandForm, redirectAttributes);

    }

    private String doUploadBrandLogo(HttpServletRequest request, Model model, //
                                     BrandForm brandForm, final RedirectAttributes  redirectAttributes) {


        System.out.println(env.getProperty("file.upload.rootPath"));

        String uploadRootPath = Paths.get(env.getProperty("file.upload.rootPath")) + "/images/brands/" + brandForm.getId() + "/";
        System.out.println("uploadRootPath=" + uploadRootPath);

        File uploadRootDir = new File(uploadRootPath);

        MultipartFile fileData = brandForm.getLogoFile();

        List<File> uploadedFiles = new ArrayList<File>();
        List<String> failedFiles = new ArrayList<String>();


        if (fileData.isEmpty()) {
            redirectAttributes.addFlashAttribute("notification", new Notification("Please choose file to upload.").warning());
            return "redirect:/admin/brand?id=" + brandForm.getId();
        }



        // Client File Name
        String name = fileData.getOriginalFilename();
        System.out.println("Client File Name = " + name);

        if (name != null && name.length() > 0) {
            try {
                FileUtils.cleanDirectory(uploadRootDir);   // delete all files in the directory

                // Create the file at server
                File serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator + name);

                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(fileData.getBytes());
                stream.close();
                //

                brandForm.setLogoFileName(name);
                uploadedFiles.add(serverFile);
                System.out.println("Write file: " + serverFile);
            } catch (Exception e) {
                System.out.println("Error Write file: " + name);
                failedFiles.add(name);
            }
        }


        brandDAO.save(brandForm);

        model.addAttribute("uploadedFiles", uploadedFiles);
        model.addAttribute("failedFiles", failedFiles);
        redirectAttributes.addFlashAttribute("message", "File uploaded :" + uploadedFiles);
        return "redirect:admin/brand?id=" + brandForm.getId();
    }


    @ModelAttribute
    public void populateInfo(Model model) {
        Info info = new Info("admin");

        List<String> brandTypes = new ArrayList<>();
        brandTypes.add("tile");
        brandTypes.add("installation");
        brandTypes.add("cabinets");

        model.addAttribute("info", info);
        model.addAttribute("brandTypes", brandTypes);
    }

}