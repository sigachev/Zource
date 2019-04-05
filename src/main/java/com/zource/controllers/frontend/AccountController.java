package com.zource.controllers.frontend;

import com.zource.dao.OrderDAO;
import com.zource.dao.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@ControllerAdvice
@Transactional
@Order(10)
public class AccountController {

    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    Environment env;


    @RequestMapping("/account")
    public String userAccount(Model model) {

        model.addAttribute("page.title", "Login Pages");

        return "/user/account";
    }


}