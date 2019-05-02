/*
 * Copyright (c) 2018.
 * Author: Mikhail Sigachev
 */

package com.zource.controllers.admin.brands;


import com.zource.dao.BrandDAO;
import com.zource.entity.Brand;
import com.zource.form.admin.brand.BrandForm;
import com.zource.form.admin.brand.BrandFormValidator;
import com.zource.model.Info;
import com.zource.model.notifications.Notification;
import com.zource.services.storage.StorageException;
import com.zource.services.storage.StorageService;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@Controller
@Transactional
public class AdminBrandsController {

    //Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = "/images/brands/";


    @Autowired
    private StorageService storageService;
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


        List<Brand> brands = brandDAO.getAllBrands();


        info.getBreadcrumb().put("Brands", "/admin/brands");
        info.getBreadcrumb().inverse();
        info.setTitle("Brands list");
        model.addAttribute("brands", brands);

        return "admin/brands/list";
    }


    // GET: new brand
    @GetMapping("/admin/brand")
    public String brand(Model model, @RequestParam(value = "id", defaultValue = "0", required = false) Integer id,
                        @ModelAttribute("info") Info info) {

        BrandForm brandForm = new BrandForm();


        if (id == (int) id) //check if integer
        {
            Brand brand = brandDAO.getBrandById(id);
            if (brand != null) {
                brandForm.setBrand(brand);

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
        model.addAttribute("brandForm", brandForm);

        return "admin/brands/brand";
    }


    // POST: Save brand
    @RequestMapping(value = {"/admin/brand/update"}, method = RequestMethod.POST)
    public String brandSave(Model model, //
                            @ModelAttribute("brandForm") @Validated BrandForm brandForm,
                            BindingResult result,
                            @ModelAttribute("info") Info info,
                            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            System.out.println("##ERRORS");

            brandForm.setBrand( brandDAO.getBrandById(brandForm.getId()));

            System.out.println("Brand form logo = " + brandForm.getLogoFileName());
            System.out.println("BrandForm id = " + brandForm.getId());
            System.out.println("Brand logo = " + brandDAO.getBrandById(brandForm.getId()).getLogo());


            model.addAttribute("notification", new Notification("ERRORS").danger());

            return "admin/brands/brand";
        }

        try {
            brandDAO.save(brandForm);
        } catch (Exception e) {
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            String message = ExceptionUtils.getStackTrace(e);
            redirectAttributes.addFlashAttribute("notification",new Notification(message).danger());

            return "redirect:/admin/brand?id=" + brandForm.getId();
        }

        redirectAttributes.addFlashAttribute("notification", new Notification("Category updated!").success());
        return "redirect:/admin/brand?id=" + brandForm.getId();
    }

    // POST: Upload Logo
    @PostMapping("/admin/brand/updateLogo")
    public String uploadBrandLogoPOST(HttpServletRequest request, //
                                      Model model, //
                                      @ModelAttribute("brandForm") BrandForm brandForm, RedirectAttributes redirectAttributes) {

      String uploadedFileName = storageService.uploadBrandLogo(brandForm.getLogoFile(), brandForm.getId());

      if (uploadedFileName.length() > 0) {
          brandForm.setLogoFileName(uploadedFileName);
          brandDAO.save(brandForm);
          redirectAttributes.addFlashAttribute("notification", new Notification("File uploaded :" + uploadedFileName).success());
      }

        return "redirect:/admin/brand?id=" + brandForm.getId();
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



    @ExceptionHandler(StorageException.class)
    public String handleStorageFileNotFound(StorageException e, final RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("notification", new Notification(e.getMessage()).warning());
        return "redirect:" + e.getPageURL();
    }
}