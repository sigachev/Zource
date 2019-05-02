/*
 * Copyright (c) 2019.
 * Author: Mikhail Sigachev
 */

package com.zource.controllers.admin.categories.REST;

import com.zource.entity.Category;
import com.zource.model.Info;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

interface AdminCategoriesRESTInterface {

    List<Category> getCategoriesTree(Model model, @RequestParam(value = "id", defaultValue = "0", required = false) Integer id,
                                     @ModelAttribute("info") Info info);


    List<Category> getAllCategories();
}
