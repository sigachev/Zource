/*
 * Copyright (c) 2018.
 * Author: Mikhail Sigachev
 */

package com.zource.controllers.frontend;

import com.zource.dao.CategoryDAO;
import com.zource.entity.category.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Set;

@Controller
@Order(12)
public class CategoryController {

    @Autowired
    private CategoryDAO categoryDAO;

    @Autowired
    Environment env;


    // Category List
    @RequestMapping({"/categories"})
    public String brandsList(Model model) {
        List<Category> searchResults = categoryDAO.getAllCategories();

        model.addAttribute("categories", searchResults);
        return "categories/all_categories";

    }


    @RequestMapping("/category/{catId}")
    public String displayBrandPage(@PathVariable(value = "catId") Integer categoryId, Model model) {

        model.addAttribute("category", categoryDAO.getCategoryByID(categoryId));

        Set<Category> subCats = categoryDAO.getChildCategories(categoryId);
        model.addAttribute("subCategories", subCats);


        return "categories/category";

    }


}