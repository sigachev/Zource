package com.zource.controllers.frontend;

import com.zource.dao.OrderDAO;
import com.zource.dao.ProductDAO;
import com.zource.form.CustomerForm;
import com.zource.model.CartInfo;
import com.zource.validator.CustomerFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Transactional
@Order(14)
public class FrontendController {

    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private ProductDAO productDAO;


    @Autowired
    private CustomerFormValidator customerFormValidator;

    @InitBinder
    public void myInitBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }
        System.out.println("Target=" + target);

        // Case update quantity in cart
        // (@ModelAttribute("cartForm") @Validated CartInfo cartForm)
        if (target.getClass() == CartInfo.class) {

        }

        // Case save customer information.
        // (@ModelAttribute @Validated CustomerInfo customerForm)
        else if (target.getClass() == CustomerForm.class) {
            dataBinder.setValidator(customerFormValidator);
        }

    }


    @RequestMapping("/403")
    public String accessDenied() {
        return "/403";
    }

    @RequestMapping(value = {"/home", "/index", "/"})
    public String home() {
        return "pages/home";
    }

    //////////////  LOGIN   //////////////////
    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String login(Model model) {

        return "login";
    }


}