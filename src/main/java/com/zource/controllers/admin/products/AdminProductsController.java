/*
 * Copyright (c) 2018.
 * Author: Mikhail Sigachev
 */

package com.zource.controllers.admin.products;

import com.zource.dao.BrandDAO;
import com.zource.dao.ProductDAO;
import com.zource.entity.Brand;
import com.zource.entity.Product;
import com.zource.form.ProductForm;
import com.zource.model.Info;
import com.zource.model.notifications.Notification;
import com.zource.validator.ProductFormValidator;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;


@ControllerAdvice
@Controller
@Transactional
@RequestMapping("/admin/products")
public class AdminProductsController {

    //Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = "/images/products/";


    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private BrandDAO brandDAO;

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
    @RequestMapping({"/", ""})
    public String brandsList(@RequestParam(value = "search", defaultValue = "", required = false) String search,
                             @ModelAttribute("info") Info info,
                             Model model) {

        List<Product> products = productDAO.getAllProducts();

        model.addAttribute("products", products);

        return "admin/products/list";
    }


    // GET: Show product.
    @GetMapping("/product")
    public String product(Model model, @RequestParam(value = "id", defaultValue = "0") Integer id,
                          @ModelAttribute("info") Info info) {
        ProductForm productForm = null;


        /*  ServletUriComponentsBuilder.fromCurrentRequest().queryParam("page", 10).toUriString();*/

        if (id == (int) id) {
            Product product = productDAO.getProductById(id);
            if (product != null) {
                productForm = new ProductForm(product);
            }
        }
        if (productForm == null) {
            productForm = new ProductForm();
            productForm.setNewProduct(true);
        }

        List<Brand> brands = brandDAO.getAllBrands();
        for (Brand x : brands)
            System.out.println(x.getName());

        info.getBreadcrumb().inverse();
        info.setTitle("Product list");

        info.setTitle(productForm.getName());

        model.addAttribute("brands", brands);
        model.addAttribute("productForm", productForm);
        return "admin/products/product";
    }


    // POST: Save product
    @PostMapping("/product")
    public String productSave(Model model, //
                              @ModelAttribute("productForm") @Validated ProductForm productForm, //
                              BindingResult result, //
                              RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "admin/products/product";
        }
        try {
            productDAO.save(productForm);
        } catch (Exception e) {
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            String message = rootCause.getMessage();
            System.out.println("MESSSAGE:  " + message);
            System.out.println("MESSAGE::::  " + message);
            model.addAttribute("errorMessage", message);
            model.addAttribute("notification", new Notification("Errors").danger());
            // Show product form.
            return "admin/products/product";
        }

        redirectAttributes.addFlashAttribute("notification", new Notification("Product updated!").success());
        return "redirect:/admin/products/product?id=" + productForm.getId();
    }


    @ModelAttribute
    public void populateInfo(Model model) {
        Info info = new Info("admin");

        info.getBreadcrumb().put("Product", "/admin/products");

        /* CSS LIST*/
        info.getCssList().add("https://cdn.jsdelivr.net/npm/@riophae/vue-treeselect@0.0.38/dist/vue-treeselect.min.css");
        info.getCssList().add("https://cdn.quilljs.com/1.3.6/quill.snow.css");

        /*TOP JS LIST*/
        info.getTopJsList().add("https://cdn.jsdelivr.net/npm/@riophae/vue-treeselect@0.0.38/dist/vue-treeselect.min.js");
        info.getTopJsList().add("https://cdn.quilljs.com/1.3.6/quill.js");

        /*BOTTOM JS LIST*/
        info.getBottomJsList().add("/admin/js/product/productParentCategoriesTree.js");


        List<String> brandTypes = new ArrayList();
        brandTypes.add("tile");
        brandTypes.add("installation");
        brandTypes.add("cabinets");

        model.addAttribute("info", info);
        model.addAttribute("brandTypes", brandTypes);
    }


}
