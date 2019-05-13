/*
 * Copyright (c) 2018.
 * Author: Mikhail Sigachev
 */
package com.zource.controllers.admin.categories;

import com.zource.dao.CategoryDAO;
import com.zource.entity.category.Category;
import com.zource.form.CategoryForm;
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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@ControllerAdvice
@Transactional
public class AdminCategoriesController {

    @Autowired
    private CategoryDAO categoryDAO;
    @Autowired
    private StorageService storageService;
    @Autowired
    Environment env;

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


    // Category List
    @GetMapping({"/admin/categories"})
    public String categoriesList(@RequestParam(value = "search", defaultValue = "", required = false) String search,
                                 @ModelAttribute("info") Info info,
                                 Model model) {

        List<Category> categories = categoryDAO.getAllCategories();
        model.addAttribute("categories", categories);

        info.getBreadcrumb().put("Category", "/admin/categories");
        info.getBreadcrumb().inverse();
        info.setTitle("Category");

        return "admin/categories/list";
    }


    // GET: Category
    @RequestMapping(value = {"/admin/category"}, method = RequestMethod.GET)
    public String brand(Model model, @RequestParam(value = "id", defaultValue = "0", required = false) Integer id,
                        @ModelAttribute("info") Info info) {

        CategoryForm catForm = new CategoryForm();

        if (id instanceof Integer) {
            Category category = categoryDAO.getCategoryByID(id);
            if (category != null)
                catForm.setCategory(category);
            else
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
    @PostMapping("/admin/category/update")
    public String categorySave(Model model, //
                               @ModelAttribute("catForm") @Valid CategoryForm catForm,
                               BindingResult bindingResult,
                               @ModelAttribute("info") Info info,
                               RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            System.out.println("##ERRORS");
            catForm.setCategory(categoryDAO.getCategoryByID(catForm.getId()));
            model.addAttribute("notification", new Notification(bindingResult.toString()).warning());
            return "admin/categories/category";
        }


        try {
            categoryDAO.save(catForm);
        } catch (Exception e) {

            Throwable rootCause = ExceptionUtils.getRootCause(e);
            String message = ExceptionUtils.getStackTrace(e);
            redirectAttributes.addFlashAttribute("notification", new Notification(message).danger());
            return "redirect:/admin/category?id=" + catForm.getId();
        }

        redirectAttributes.addFlashAttribute("notification", new Notification("Category updated!").success());
        return "redirect:/admin/category?id=" + catForm.getId();
    }


    // POST: Do Upload
    @PostMapping("/admin/category/updateLogo")
    public String uploadCategoryLogoPOST(Model model,
                                         @ModelAttribute("categoryForm") CategoryForm categoryForm, RedirectAttributes redirectAttributes) {

        String uploadedFileName = storageService.updateCategoryLogo(categoryForm.getLogoFile(), categoryDAO.getCategoryByID(categoryForm.getId()));

        if (uploadedFileName.length() > 0) {
            categoryForm.setLogoFileName(uploadedFileName);
            categoryDAO.save(categoryForm);
            redirectAttributes.addFlashAttribute("notification", new Notification("File uploaded :" + uploadedFileName).success());
        }

        return "redirect:/admin/category?id=" + categoryForm.getId();
    }


    @ModelAttribute
    public void populateInfo(Model model) {
        Info info = new Info("admin");

        /* CSS LIST*/
        info.getCssList().add("https://cdn.jsdelivr.net/npm/@riophae/vue-treeselect@0.0.38/dist/vue-treeselect.min.css");

        /*TOP JS LIST*/
        info.getTopJsList().add("https://cdn.jsdelivr.net/npm/@riophae/vue-treeselect@0.0.38/dist/vue-treeselect.min.js");

        /*BOTTOM JS LIST*/
        info.getBottomJsList().add("/admin/js/category/categoryTree.js");

        List<String> brandTypes = new ArrayList<>();
        brandTypes.add("tile");
        brandTypes.add("installation");
        brandTypes.add("cabinets");

        model.addAttribute("info", info);
        model.addAttribute("catTypes", brandTypes);
    }


    @ExceptionHandler(StorageException.class)
    public String handleStorageFileNotFound(StorageException e, final RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("notification", new Notification(e.getMessage()).warning());
        return "redirect:" + e.getPageURL();
    }

}