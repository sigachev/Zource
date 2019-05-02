package com.zource.controllers.frontend;

import com.zource.dao.PageDAO;
import com.zource.entity.Pages;
import com.zource.form.EmailForm;
import com.zource.model.Info;
import com.zource.services.email.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@ControllerAdvice
@PropertySource("classpath:/messages/general.properties")
//@Transactional
@Order(15)
public class PagesController {


    @Autowired
    private PageDAO pageDAO;

    @Autowired
    Environment env;

    @Autowired
    public EmailServiceImpl emailService;

    @Autowired
    public SimpleMailMessage template;

    @Autowired
    private MessageSource messageSource;


  
/*    @Value("${store.name}")
    private String storeName;*/

    @Value("${store.email.main}")
    private String storeEmail;


    @RequestMapping("/page/{id}")
    public String getPage(@PathVariable(value = "id") Integer pageId, @ModelAttribute("info") Info info, Model model) {

        Pages page = pageDAO.getPageByID(pageId);

        info.getBreadcrumb().put(page.getTitle(), "/page/" + pageId.toString());
        info.getBreadcrumb().inverse();

        info.setInfo(page);
        model.addAttribute("page", page);

        return "pages/page";

    }


/*
    @RequestMapping("/faq")
    public String faq(@ModelAttribute("page") PageInfo page) {

        page.title = "FAQ";
        page.metaTitle="FAQ";
        page.metaDesc="Frequently asked questions - how our store works";
        page.metaDesc="Help with the most popular questions about our store";

       return "/faq";
    }*/


    @RequestMapping(value = "/contacts", method = RequestMethod.GET)
    public String contactUs(Model model, @ModelAttribute("info") Info info) {

        EmailForm emailForm = new EmailForm();


        info.setTitle("Contacts");
        info.setMetaTitle(env.getProperty("store.name") + " - Contacts");
        info.setMetaDescription(env.getProperty("store.name") + ". Our showrooms locations and working hours");
        info.setMetaDescription("Store locations, working hours, directions.");
        info.getBreadcrumb().put("Contacts", "/contacts");
        info.getBreadcrumb().inverse();

        model.addAttribute("emailForm", emailForm);

        return "pages/contacts";
    }


    //Send email from contact form on contact_us page: page/1
    @RequestMapping(value = "/contacts", method = RequestMethod.POST)
    public String contacts(Model model,
                           @ModelAttribute("info") Info info,
                           @ModelAttribute("emailForm") @Valid EmailForm emailForm,
                           Errors errors) {


        if (errors.hasErrors()) {
            System.out.println("Email ERRORS.");
            model.addAttribute("errorMessage", errors);
            /*       model.addAttribute(info);*/
            return "pages/contacts";
        }

        emailForm.setTo(env.getProperty("store.email.send.from"));

        emailService.sendSimpleMessageUsingTemplate(emailForm.getTo(),
                emailForm.getSubject(),
                template,
                emailForm.getMessage());
        System.out.println("Email sent.");

        info.setTitle("Contacts");
        info.setMetaTitle(env.getProperty("store.name") + " - Contacts");
        info.setMetaDescription(env.getProperty("store.name") + ". Our showrooms locations and working hours");
        info.setMetaDescription("Store locations, working hours, directions.");
        info.getBreadcrumb().put("Contacts", "/contacts");
        info.getBreadcrumb().inverse();

        model.addAttribute("errorMessage", "Your message has been sent. Thank you.");


        return "pages/contacts";
    }


    @ModelAttribute("info")
    public Info Info() {
        Info info = new Info("frontend");
        return info;
    }


}