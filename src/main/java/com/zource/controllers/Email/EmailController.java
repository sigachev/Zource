/*
 * Copyright (c) 2018.
 * Author: Mikhail Sigachev
 */

package com.zource.controllers.Email;

import com.zource.form.EmailForm;
import com.zource.services.email.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/mail")
public class EmailController {

    @Autowired
    public EmailServiceImpl emailService;

    /*   @Value("${attachment.invoice}")*/
    private String attachmentPath;

    @Autowired
    Environment env;

    @Autowired
    public SimpleMailMessage template;

    private static final Map<String, Map<String, String>> labels;

    static {
        labels = new HashMap<>();

        //Simple email
        Map<String, String> props = new HashMap<>();
        props.put("headerText", "Send Simple Email");
        props.put("messageLabel", "Message");
        props.put("additionalInfo", "");
        labels.put("send", props);

        //Email with template
        props = new HashMap<>();
        props.put("headerText", "Send Email Using Template");
        props.put("messageLabel", "Template Parameter");
        props.put("additionalInfo",
                "The parameter value will be added to the following message template:<br>" +
                        "<b>This is the test email template for your email:<br>'Template Parameter'</b>"
        );
        labels.put("sendTemplate", props);

        //Email with attachment
        props = new HashMap<>();
        props.put("headerText", "Send Email With Attachment");
        props.put("messageLabel", "Message");
        props.put("additionalInfo", "To make sure that you send an attachment with this email, change the value for the 'attachment.invoice' in the application.properties file to the path to the attachment.");
        labels.put("sendAttachment", props);
    }

    @RequestMapping(value = {"/send", "/sendTemplate", "/sendAttachment"}, method = RequestMethod.GET)
    public String createMail(Model model,
                             HttpServletRequest request) {
        String action = request.getRequestURL().substring(
                request.getRequestURL().lastIndexOf("/") + 1
        );
        Map<String, String> props = labels.get(action);
        Set<String> keys = props.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            model.addAttribute(key, props.get(key));
        }

        /* model.addAttribute("emailForm", new EmailForm());*/
        return "mail/send";
    }

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public String createMail(Model model,
                             @ModelAttribute("EmailForm") @Valid EmailForm emailForm,
                             Errors errors) {
        if (errors.hasErrors()) {
            return "mail/send";
        }
        emailService.sendSimpleMessage(emailForm.getTo(),
                emailForm.getSubject(), emailForm.getMessage());

        return "redirect:/home";
    }


    //Send email from contact form on contact_us page: page/1
    @RequestMapping(value = "/contactFormEmail", method = RequestMethod.POST)
    public String createMailWithTemplate(Model model,
                                         @ModelAttribute("emailForm") @Valid EmailForm emailForm,
                                         Errors errors) {
        if (errors.hasErrors()) {
            System.out.println("Email ERRORS.");
            model.addAttribute("errorMessage", errors);
            return "pages/page/1";
        }
        emailService.sendSimpleMessageUsingTemplate("masstilegesign@gmail.com",
                emailForm.getSubject(),
                template,
                emailForm.getMessage());
        System.out.println("Email sent.");
        model.addAttribute("errorMessage", "Your message has been sent. Thank you.");

        return "redirect:/page/1";
    }

    @RequestMapping(value = "/sendAttachment", method = RequestMethod.POST)
    public String createMailWithAttachment(Model model,
                                           @ModelAttribute("EmailForm") @Valid EmailForm emailForm,
                                           Errors errors) {
        if (errors.hasErrors()) {
            return "mail/send";
        }
        emailService.sendMessageWithAttachment(
                emailForm.getTo(),
                emailForm.getSubject(),
                emailForm.getMessage(),
                attachmentPath
        );

        return "redirect:/home";
    }

    @ModelAttribute("emailForm")
    public EmailForm info() {
        EmailForm emailForm = new EmailForm();
        emailForm.setTo(env.getProperty("store.email.send.from"));
        return emailForm;
    }
}