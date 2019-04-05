/*
 * Copyright (c) 2018.
 * Author: Mikhail Sigachev
 */

package com.zource.controllers.frontend;

import com.zource.dao.ProductDAO;
import com.zource.entity.Products;
import com.zource.model.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@ControllerAdvice
public class ProductsController {

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    Environment env;


    @RequestMapping("/product/{id}")
    public String getPage(@PathVariable(value = "id") Integer productId, @ModelAttribute("info") Info info, Model model) {

        Products product = productDAO.getProductById(productId);

        /*info.getBreadcrumb().put(product.getName(), "/product/"+ productId.toString());
        info.getBreadcrumb().inverse();

        info.setInfo(product);*/

        /*info.getTopCssList().add("/css/photoswipe.css");
        info.getTopCssList().add("/css/photoswipe-default-skin/default-skin.min.css");*/


        model.addAttribute("product", product);

        return "product/productLayout";

    }


    @ModelAttribute
    public void info(Model model) {
        Info info = new Info("frontend");
        info.getCssList().add("/css/photoswipe.css");
        info.getCssList().add("/css/photoswipe-default-skin/default-skin.min.css");

        info.getBottomJsList().add("/js/photoswipe.min.js");
        info.getBottomJsList().add("/js/photoswipe-ui-default.min.js");
        info.getBottomJsList().add("/js/jquery.raty.min.js");

        model.addAttribute("info", info);
    }

}
