/*
 * Copyright (c) 2018.
 * Author: Mikhail Sigachev
 */

package com.zource.controllers.admin.categories;

import com.zource.dao.CategoryDAO;
import com.zource.entity.Categories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class CategoriesTreeController {

    @Autowired
    CategoryDAO categoryDAO;

    @RequestMapping(value = "/admin/catTree", produces = "application/json")
    public @ResponseBody
    List<Categories> getCategoriesJSON() {


        return categoryDAO.getAllCategories();

    }


}
