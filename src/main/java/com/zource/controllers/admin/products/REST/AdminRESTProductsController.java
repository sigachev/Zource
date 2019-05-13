/*
 * Copyright (c) 2019.
 * Author: Mikhail Sigachev
 */
package com.zource.controllers.admin.products.REST;

import com.fasterxml.jackson.annotation.JsonView;
import com.zource.dao.ProductDAO;
import com.zource.entity.category.Category;
import com.zource.entity.category.CategoryViews;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;


@RestController
@RequestMapping("/admin/rest/products")
public class AdminRESTProductsController {

    @Autowired
    ProductDAO productDAO;


    // GET: Category
    @GetMapping("/getProductCategories")
    @JsonView(CategoryViews.ChildrenView.class)
    public ResponseEntity<Set> getProductCategories(@RequestParam(value = "id", required = true, defaultValue = "0") Integer id) {
        return new ResponseEntity(productDAO.getProductById(id).getCategories(), HttpStatus.OK);

    }

    @GetMapping("/getProductCategoriesIDs")
    @JsonView(CategoryViews.ChildrenView.class)
    public ResponseEntity<Set> getProductCategoriesIDs(@RequestParam(value = "id", required = true, defaultValue = "0") Integer id) {

        Set<Integer> result = new HashSet<>();

        if ((id == 0) || productDAO.getProductById(id).getCategories().isEmpty())
            return new ResponseEntity(result, HttpStatus.OK);

        for (Category c : productDAO.getProductById(id).getCategories())
            result.add(c.getId());
        return new ResponseEntity(result, HttpStatus.OK);

    }
}
