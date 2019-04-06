/*
 * Copyright (c) 2018.
 * Author: Mikhail Sigachev
 */
package com.zource.controllers.admin.Categories;

import com.zource.controllers.admin.BrandFormValidator;
import com.zource.dao.CategoryDAO;
import com.zource.entity.Categories;
import com.zource.form.CategoryForm;
import com.zource.model.Info;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminCategoriesController {

    @Autowired
    private CategoryDAO categoryDAO;

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

        if (target.getClass() == CategoryForm.class) {
            //dataBinder.addValidators(brandFormValidator);   //add or set
        }
    }



    // Categories List
    @RequestMapping({"/admin/categories"})
    public String categoriesList(@RequestParam(value = "search", defaultValue = "", required = false) String search,
                                 @ModelAttribute("info") Info info,
                                 Model model) {

        List<Categories> categories = categoryDAO.getAllCategories();
        model.addAttribute("categories", categories);

        info.getBreadcrumb().put("Categories", "/admin/categories");
        info.getBreadcrumb().inverse();
        info.setTitle("Categories");

        return "admin/categories/list";

    }


    // GET: Category
    @RequestMapping(value = {"/admin/category"}, method = RequestMethod.GET)
    public String brand(Model model, @RequestParam(value = "id", defaultValue = "0", required = false) Integer id,
                        @ModelAttribute("info") Info info) {

        CategoryForm catForm = null;


        if (id instanceof Integer) {
            Categories category = categoryDAO.getCategoryByID(id);
            if (category != null) {
                catForm = new CategoryForm(category);
            }
        }
        if (catForm == null) {
            catForm = new CategoryForm();
            catForm.setNewCategory(true);
        }

        info.setTitle("Category Info");
        info.getBreadcrumb().put("Categories", "/admin/categories");
        info.getBreadcrumb().put(catForm.getName(), "");
        info.getBreadcrumb().inverse();
        model.addAttribute("catForm", catForm);


        return "admin/categories/category";
    }

    // POST: Save category
    @PostMapping("/admin/category")
    public String categorySave(Model model, //
                               @ModelAttribute("catForm") @Valid CategoryForm catForm,
                               BindingResult bindingResult,
                               @ModelAttribute("info") Info info,
                               final RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            System.out.println("##ERRORS");
            return "admin/categories/category";
        }


        try {
            categoryDAO.save(catForm);

        }
        catch (Exception e) {
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            String message = ExceptionUtils.getStackTrace(e);
            model.addAttribute("errorMessage", message);
            // Show product form.
            return "admin/categories/category";
        }

        redirectAttributes.addFlashAttribute("message", "Category updated!");
        redirectAttributes.addFlashAttribute("messageType", "success");

        return "redirect:/admin/category?id=" + catForm.getId();
    }


    // POST: Do Upload
    @PostMapping("/uploadCategoryImage")
    public String uploadCategoryLogoPOST(HttpServletRequest request, //
                                      Model model, //
                                      @ModelAttribute("catForm") CategoryForm catForm,
                                         RedirectAttributes redirectAttributes) {

        return this.doUploadCategoryImage(request, model, catForm, redirectAttributes);

    }

    private String doUploadCategoryImage(HttpServletRequest request, Model model, //
                                         CategoryForm catForm, final RedirectAttributes  redirectAttributes) {


        catForm = (CategoryForm) model.asMap().get("catForm");

        System.out.println("CatForm ID = " + catForm.getId());

        System.out.println(env.getProperty("file.upload.rootPath"));

        String uploadRootPath = Paths.get(env.getProperty("file.upload.rootPath")) + "/images/categories/" + catForm.getId() + "/";
        System.out.println("uploadRootPath=" + uploadRootPath);

        File uploadRootDir = new File(uploadRootPath);

        MultipartFile fileData = catForm.getImageFile();

        List<File> uploadedFiles = new ArrayList<File>();
        List<String> failedFiles = new ArrayList<String>();


        ///////////////////////

        if (fileData.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:/admin/category?id=" + catForm.getId();
        }


        ///////////////////////



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

                catForm.setImageFileName(name);
                uploadedFiles.add(serverFile);
                System.out.println("Write file: " + serverFile);
            } catch (Exception e) {
                System.out.println("Error Write file: " + name);
                failedFiles.add(name);
            }
        }


        categoryDAO.save(catForm);

        model.addAttribute("uploadedFiles", uploadedFiles);
        model.addAttribute("failedFiles", failedFiles);
        return "redirect:admin/category?id=" + catForm.getId();
    }


    @ModelAttribute
    public void populateInfo(Model model) {
        Info info = new Info("admin");

        List<String> brandTypes = new ArrayList<>();
        brandTypes.add("tile");
        brandTypes.add("installation");
        brandTypes.add("cabinets");

        model.addAttribute("info", info);
        model.addAttribute("catTypes", brandTypes);
    }

}