/*
 * Copyright (c) 2018.
 * Author: Mikhail Sigachev
 */
package com.zource.controllers.admin.categories;

import com.zource.dao.BrandDAO;
import com.zource.dao.CategoryDAO;
import com.zource.entity.category.Category;
import com.zource.form.admin.category.CategoryForm;
import com.zource.form.admin.category.CategoryLogoForm;
import com.zource.model.Info;
import com.zource.model.notifications.Notification;
import com.zource.services.CategoryService;
import com.zource.services.category.CategoryException;
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
import java.util.List;

@Controller
@ControllerAdvice
@SessionAttributes("loginForm")
public class AdminCategoriesController {

    @Autowired
    private CategoryDAO categoryDAO;

    @Autowired
    private BrandDAO brandDAO;

    @Autowired
    private CategoryService categoryService;

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
    @Transactional
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
                        @ModelAttribute("catForm") CategoryForm catForm,
                        @ModelAttribute("logoForm") CategoryLogoForm logoForm,
                        @ModelAttribute("info") Info info) {


        if (id instanceof Integer) {
            Category category = categoryService.getById(id);
            if (category != null) {
                catForm.update(category);
                logoForm.updateLogo(category);
            } else
                throw new CategoryException("No such category found.");
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
                               @ModelAttribute CategoryLogoForm logoForm,
                               @ModelAttribute("catForm") @Valid CategoryForm categoryForm,
                               BindingResult bindingResult,
                               @ModelAttribute("info") Info info,
                               RedirectAttributes redirectAttributes) {

        Category category = categoryDAO.getById(categoryForm.getId());
        category.update(categoryForm);
        logoForm.updateLogo(category);

        if (bindingResult.hasErrors()) {
            System.out.println("##ERRORS");
            model.addAttribute("notification", new Notification(bindingResult.toString()).warning());
            return "admin/categories/category";
        }

        //Check for parent categories loop
        if (categoryForm.getParentCategories() != null)
            for (Category c : categoryForm.getParentCategories())
                if (categoryDAO.isParentLoop(categoryDAO.getById(categoryForm.getId()), c)) {
                    model.addAttribute("notification", new Notification("Cannot assign parent category with id = " + c.getId() + " and name = \"" + c.getName() + "\". It creates a parent category loop.").warning());
                    return "admin/categories/category";
                }


        try {
            categoryDAO.update(category);
        } catch (Exception e) {

            /*   Throwable rootCause = ExceptionUtils.getRootCause(e);*/
            String message = ExceptionUtils.getStackTrace(e);
          /*  redirectAttributes.addFlashAttribute("notification", new Notification(message).danger());
            return "redirect:/admin/category?id=" + categoryForm.getId();*/

            throw new CategoryException(message);
        }

        redirectAttributes.addFlashAttribute("notification", new Notification("Category updated!").success());
        return "redirect:/admin/category?id=" + categoryForm.getId();
    }


    // POST: Do Upload
    @PostMapping("/admin/category/updateLogo")
    public String uploadCategoryLogoPOST(Model model, @ModelAttribute("logoForm") CategoryLogoForm logoForm, RedirectAttributes redirectAttributes) {

        Category category = categoryDAO.getById(logoForm.getId());
        storageService.updateCategoryLogo(logoForm.getLogoFile(), category);
        redirectAttributes.addFlashAttribute("notification", new Notification("File uploaded :" + logoForm.getLogoFileName()).success());


        return "redirect:/admin/category?id=" + logoForm.getId();
    }


    @ModelAttribute
    public void populateModel(Model model) {
        Info info = new Info("admin");

        /* CSS LIST*/
        info.getCssList().add("https://cdn.jsdelivr.net/npm/@riophae/vue-treeselect@0.0.38/dist/vue-treeselect.min.css");

        /*TOP JS LIST*/
        info.getTopJsList().add("https://cdn.jsdelivr.net/npm/@riophae/vue-treeselect@0.0.38/dist/vue-treeselect.min.js");

        /*BOTTOM JS LIST*/
        info.getBottomJsList().add("/admin/js/category/categoryTree.js");

        model.addAttribute("brands", brandDAO.getAllBrands());
        model.addAttribute("info", info);

    }


    @ExceptionHandler(StorageException.class)
    public String handleStorageFileNotFound(StorageException e, final RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("notification", new Notification(e.getMessage()).warning());
        return "redirect:" + e.getPageURL();
    }

    @ExceptionHandler(CategoryException.class)
    public String handleCategoryExceptions(CategoryException e, final RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("notification", new Notification(e.getMessage()).warning());
        return "redirect:" + e.getPageURL();
    }

}