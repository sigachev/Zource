/*
 * Copyright (c) 2018.
 * Author: Mikhail Sigachev
 */
package com.zource.controllers.admin.Categories;

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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
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
    @RequestMapping(value = {"/admin/category"}, method = RequestMethod.POST)
    public String categorySave(Model model, //
                               @ModelAttribute("catForm") @Validated CategoryForm catForm,
                               BindingResult result,
                               @ModelAttribute("info") Info info,
                               final RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            System.out.println("##ERRORS");
            return "admin/categories/category";
        }


        try {
            System.out.println("##before save ");
            categoryDAO.save(catForm);
        } catch (Exception e) {
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            String message = ExceptionUtils.getStackTrace(e);
            model.addAttribute("errorMessage", message);
            // Show product form.
            return "admin/brands/brand";
        }

        redirectAttributes.addFlashAttribute("successMsg", "Category updated!");

        return "redirect:/admin/category?id=" + catForm.getId();
    }


    // POST: Do Upload
    @RequestMapping(value = "/uploadCategoryImage", method = RequestMethod.POST)
    public String uploadBrandLogoPOST(HttpServletRequest request, //
                                      Model model, //
                                      @ModelAttribute("catForm") CategoryForm catForm) {


        return this.doUploadCategoryImage(request, model, catForm);

    }

    private String doUploadCategoryImage(HttpServletRequest request, Model model, //
                                         CategoryForm catForm) {


        catForm = (CategoryForm) model.asMap().get("catForm");

        System.out.println(env.getProperty("file.upload.rootPath"));

        String uploadRootPath = Paths.get(env.getProperty("file.upload.rootPath")) + "/images/categories/" + catForm.getId() + "/";
        System.out.println("uploadRootPath=" + uploadRootPath);

        File uploadRootDir = new File(uploadRootPath);

        MultipartFile fileData = catForm.getImageFile();

        List<File> uploadedFiles = new ArrayList<File>();
        List<String> failedFiles = new ArrayList<String>();


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
        return "admin/brand?id=" + catForm.getId();
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