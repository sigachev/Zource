/*
 * Copyright (c) 2018.
 * Author: Mikhail Sigachev
 */
package com.zource.controllers.admin.content;

import com.zource.dao.PageDAO;
import com.zource.dao.PageMenuDAO;
import com.zource.entity.PageMenu;
import com.zource.entity.Pages;
import com.zource.form.PageForm;
import com.zource.model.Info;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Transactional
@RequestMapping("/admin/content/")
public class AdminPagesController {

    @Autowired
    private PageDAO pageDAO;

    @Autowired
    private PageMenuDAO menuDAO;

    @Autowired
    Environment env;


/*
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Pages.class, new PageEditor());
    }
*/


    // Category List
    @RequestMapping({"/pages"})
    public String categoriesList(@RequestParam(value = "search", defaultValue = "", required = false) String search,
                                 Model model, @ModelAttribute("info") Info info) {

        List<Pages> pages = pageDAO.getAllPages();

        info.getBreadcrumb().put("Pages", "/admin/content/pages");
        info.getBreadcrumb().inverse();
        info.setTitle("Pages list");

        model.addAttribute("pages", pages);

        return "admin/content/pages/list";
    }


    // GET: Page
    @RequestMapping(value = {"/page"}, method = RequestMethod.GET)
    public String brand(Model model, @RequestParam(value = "id", defaultValue = "0", required = false) Integer id,
                        @ModelAttribute("info") Info info) {

        PageForm pageForm = null;

        if (id instanceof Integer) {
            Pages page = pageDAO.getPageByID(id);
            if (page != null) {
                pageForm = new PageForm(page);
            }
        }

        if (pageForm == null) {
            pageForm = new PageForm();
            /*  pageForm.setNewPage(true);*/
        }

        List<PageMenu> pageMenus = menuDAO.getAllPageMenus();

        //Page side menus
        model.addAttribute("pageMenus", pageMenus);


        info.setTitle("Page: " + pageForm.getTitle());
        info.getBreadcrumb().put("Pages", "/admin/content/pages");
        info.getBreadcrumb().put("Page", "");
        info.getBreadcrumb().inverse();
        model.addAttribute("pageForm", pageForm);

        return "admin/content/pages/page";
    }


    // POST: Pages
    @RequestMapping(value = {"/page"}, method = RequestMethod.POST)
    public String pageSave(Model model, //
                           @ModelAttribute("pageForm") @Validated PageForm pageForm,
                           BindingResult result,
                           @ModelAttribute("info") Info info,
                           final RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            System.out.println("##ERRORS");
            return "admin/content/pages/page";
        }
        try {
            System.out.println("##before save ");
            pageDAO.save(pageForm);
        } catch (Exception e) {
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            String message = ExceptionUtils.getStackTrace(e);
            model.addAttribute("errorMessage", message);
            // Show product form.
            return "admin/content/pages/page";
        }

        return "redirect:/admin/content/pages";
    }


    @ModelAttribute("info")
    public void populateInfo(Model model) {
        Info info = new Info("admin");

    }


}