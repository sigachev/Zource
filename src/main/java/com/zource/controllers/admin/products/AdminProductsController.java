/*
 * Copyright (c) 2018.
 * Author: Mikhail Sigachev
 */

package com.zource.controllers.admin.products;

import com.zource.dao.ProductDAO;
import com.zource.entity.Products;
import com.zource.model.Info;
import com.zource.validator.ProductFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;


@ControllerAdvice
@Controller
@Transactional
public class AdminProductsController {

    //Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = "/images/products/";


    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private ProductFormValidator productFormValidator;

    @Autowired
    Environment env;

/*    @InitBinder
    public void myInitBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }
        System.out.println("Target=" + target);

        if (target.getClass() == BrandForm.class) {
            dataBinder.addValidators(productFormValidator);   //add or set
        }
    }*/

    // List all products
    @RequestMapping("/admin/products")
    public String brandsList(@RequestParam(value = "search", defaultValue = "", required = false) String search,
                             @ModelAttribute("info") Info info,
                             Model model) {

        List<Products> products = productDAO.getAllProducts();

        info.getBreadcrumb().inverse();
        info.setTitle("Product list");
        model.addAttribute("products", products);

        return "admin/products/list";
    }


    @ModelAttribute
    public void populateInfo(Model model) {
        Info info = new Info("admin");

        info.getBreadcrumb().put("Products", "/admin/products");

        List<String> brandTypes = new ArrayList<>();
        brandTypes.add("tile");
        brandTypes.add("installation");
        brandTypes.add("cabinets");

        model.addAttribute("info", info);
        model.addAttribute("brandTypes", brandTypes);
    }


}
