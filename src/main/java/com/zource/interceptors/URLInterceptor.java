/*
 * Copyright (c) 2018.
 * Author: Mikhail Sigachev
 */

package com.zource.interceptors;

import com.zource.dao.URLRedirectDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class URLInterceptor implements HandlerInterceptor {

    @Autowired
    private URLRedirectDAO urlRedirectDAO;


    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        System.out.println("**********************     BEGIN    *********************");
        System.out.println("Request ContextPath:" + request.getContextPath());
        System.out.println("Request URI:" + request.getRequestURI());


        //urlRedirectDAO = new URLRedirectDAO();
        String redirect_to = urlRedirectDAO.getOriginalURL(request.getRequestURI());


        System.out.println("**********************      END     *********************");
        System.out.println();

        if (!redirect_to.equals("void")) {
            System.out.println("Redirected to: " + redirect_to);
            request.getRequestDispatcher(redirect_to).forward(request, response);
            return false;
        }


        return true;


    }
}
