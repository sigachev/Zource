package com.zource.controllers.frontend;

import com.zource.dao.BrandDAO;
import com.zource.dao.URLRedirectDAO;
import com.zource.entity.Brand;
import com.zource.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@Order(11)
public class BrandController {

    @Autowired
    private BrandDAO brandDAO;

    @Autowired
    Environment env;
    @Autowired
    private URLRedirectDAO urlRedirectDAO;


    // Brands List
    @RequestMapping({"/brands"})
    public String brandsList(@RequestParam(value = "search", defaultValue = "", required = false) String search,
                             @RequestParam(value = "type", defaultValue = "", required = false) String type,
                             Model model) {
        List<Brand> searchResults = brandDAO.findBrands(search, type);


        model.addAttribute("brands", searchResults);

        String redirect_to = urlRedirectDAO.getOriginalURL("/tile");
        model.addAttribute("redirect_to", redirect_to);

        return "brands/brands";

    }


    @RequestMapping({"/brand/{id}"})
    public String displayBrandPage(@PathVariable(value = "id", required = false) String brandId, Model model) {
        List<Category> searchResults = brandDAO.getBrandCategories(brandId);

        model.addAttribute("brandCategories", searchResults);
        model.addAttribute("brandDescription", brandDAO.getBrandById(Integer.parseInt(brandId)));
        model.addAttribute("brand", brandDAO.getBrandById(Integer.parseInt(brandId)));
        return "brands/brand";

    }


}