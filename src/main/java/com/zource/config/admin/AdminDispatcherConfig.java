/*
package com.zource.config.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

    @Configuration
    @EnableWebMvc
    @ComponentScan(basePackages = { "com.zource.controllers.admin" ,
                                    "com.zource.config.admin"})
    public class AdminDispatcherConfig extends WebMvcConfigurationSupport {

        @Autowired
        private ApplicationContext applicationContext;

        @Bean
        public SpringResourceTemplateResolver templateResolverAdmin() {
            SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
            templateResolver.setApplicationContext(applicationContext);
            templateResolver.setPrefix("/WEB-INF/pages/admin/");
            templateResolver.setSuffix(".html");
            templateResolver.setCheckExistence(true);
            templateResolver.setTemplateMode("HTML");
            return templateResolver;
        }

     //   @Bean
        public SpringTemplateEngine templateEngine() {
            SpringTemplateEngine templateEngine = new SpringTemplateEngine();
            templateEngine.setTemplateResolver(templateResolverAdmin());
            templateEngine.setEnableSpringELCompiler(true);
            templateEngine.addDialect(new SpringSecurityDialect());
            return templateEngine;
        }

        @Bean (name = "adminViewResolver")
        @Description("Thymeleaf view resolver")
        public ViewResolver viewResolver() {
            ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
            viewResolver.setTemplateEngine(templateEngine());
            viewResolver.setCharacterEncoding("UTF-8");

            return viewResolver;
        }

        @Override
        public RequestMappingHandlerMapping requestMappingHandlerMapping() {
            RequestMappingHandlerMapping handlerMapping = super.requestMappingHandlerMapping();
            handlerMapping.setAlwaysUseFullPath(true);
            return handlerMapping;
        }

        ///////////////////////////////////////////////////////////////////////

        @Bean
        public MessageSource messageSource() {
            ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
            // Load file: validation.properties
            messageSource.setBasename("classpath:validation");
            messageSource.setDefaultEncoding("UTF-8");
            return messageSource;
        }


        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler(
                    "/a/images/**",
                    "/a/img/**",
                    "/a/css/**",
                    "/a/js/**",
                    "/a/fonts/**",
                    "/a/font-awesome/**",
                    "/a/email-templates/**")
                    .addResourceLocations(
                            "classpath:/admin/static/images/",
                            "classpath:/admin/static/img/",
                            "classpath:/admin/static/css/",
                            "classpath:/admin/static/js/",
                            "classpath:/admin/static/fonts/",
                            "classpath:/admin/static/font-awesome/",
                            "classpath:/admin/static/email-templates/");
        }



}*/
