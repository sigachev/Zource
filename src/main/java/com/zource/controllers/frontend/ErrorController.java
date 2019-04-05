/*
 * Copyright (c) 2018.
 * Author: Mikhail Sigachev
 */

package com.zource.controllers.frontend;

import com.zource.model.Info;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@Order(99)
public class ErrorController {

    @RequestMapping(value = "error", method = RequestMethod.GET)
    public ModelAndView renderErrorPage(HttpServletRequest httpRequest, @ModelAttribute("info") Info info, Model model) {

        ModelAndView errorPage = new ModelAndView("pages/errorPage");

        String errorMsg = "";

        int httpErrorCode = getErrorCode(httpRequest);

        info.setMetaTitle(httpErrorCode + " error");
        errorPage.addObject("info.metaTitle", httpErrorCode + " error");

        switch (httpErrorCode) {
            case 400: {
                errorMsg = "Http Error Code: 400. Bad Request";
                break;
            }
            case 401: {
                errorMsg = "Http Error Code: 401. Unauthorized";
                break;
            }
            case 404: {
                errorMsg = "Http Error Code: 404. Resource not found";
                break;
            }
            case 500: {
                errorMsg = "Http Error Code: 500. Internal Server Error";
                break;
            }
        }
        info.getBreadcrumb().put("Error Page", "");
        info.getBreadcrumb().inverse();
        return errorPage;
    }

    private int getErrorCode(HttpServletRequest httpRequest) {
        return (Integer) httpRequest
                .getAttribute("javax.servlet.error.status_code");
    }

    @RequestMapping(value = "/*", method = RequestMethod.GET)
    public String redirectEverythingOtherThanTest() {
        return "pages/errorPage";
    }
}