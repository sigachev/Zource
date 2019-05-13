package com.zource.config.frontend;


import com.zource.controllers.admin.brands.BrandFormatter;
import com.zource.entity.category.CategoryConverter;
import com.zource.entity.category.CategorySetFormatter;
import com.zource.interceptors.URLInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import java.util.Collections;

//import org.thymeleaf.extras.springsecurity.dialect.SpringSecurityDialect;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.zource.controllers", "com.zource.validator", "com.zource.tests"})
public class FrontendDispatcherConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    BrandFormatter brandFormatter;
    @Autowired
    CategoryConverter categoryConverter;
    @Autowired
    CategorySetFormatter categorySetFormatter;


    @Bean
    public SpringResourceTemplateResolver adminTemplateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/WEB-INF/pages/");
        templateResolver.setSuffix(".html");
        templateResolver.setResolvablePatterns(Collections.singleton("admin/**"));
        templateResolver.setOrder(1);
        templateResolver.setCheckExistence(true);
        templateResolver.setTemplateMode("HTML");
        return templateResolver;
    }


    @Bean
    public SpringResourceTemplateResolver frontendTemplateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/WEB-INF/pages/frontend/");
        templateResolver.setSuffix(".html");
        templateResolver.setOrder(2);
        templateResolver.setCheckExistence(true);
        templateResolver.setTemplateMode("HTML");
        return templateResolver;
    }


    @Bean(name = "templateEngineFrontend")
    public SpringTemplateEngine templateEngineFrontend() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        //templateEngine.addDialect(new SpringSecurityDialect());
        templateEngine.addTemplateResolver(adminTemplateResolver());
        templateEngine.addTemplateResolver(frontendTemplateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }


    @Bean(name = "frontendViewResolver")
    @Description("Thymeleaf view resolver")
    public ViewResolver viewResolver() {

        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngineFrontend());
        viewResolver.setCharacterEncoding("UTF-8");

        return viewResolver;
    }


    ////////////////////  URL Redirect interceptor  ////////////////////////

    @Bean
    public URLInterceptor URLInterceptor() {
        return new URLInterceptor();
    }

    public @Override
    void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(URLInterceptor())
                .excludePathPatterns(
                        "/images/**",
                        "/css/**",
                        "/js/**",
                        "/fonts/**",
                        "/bootstrap/**",
                        "/error**",
                        "/product**",
                        "/category**",
                        "/",
                        "/admin/**",
                        "/blocks/**",
                        "/page/**",
                        "/img/**",
                        "/favicon**",
                        "/#**");
    }


    ///////////////////////////////////////////////////////////////////////


    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        // Load file: validation.properties
        messageSource.setBasenames("classpath:messages/general", "classpath:messages/validation");
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(
                "/images/**",
                "/css/**",
                "/js/**",
                "/fonts/**",
                "/bootstrap/**",
                "/admin/images/**",
                "/admin/img/**",
                "/admin/css/**",
                "/admin/js/**",
                "/admin/fonts/**",
                "/admin/font-awesome/**",
                "/admin/email-templates/**",
                "/node_modules/**"
        )
                .addResourceLocations(
                        "classpath:/images/",
                        "classpath:/frontend/static/css/",
                        "classpath:/frontend/static/js/",
                        "classpath:/frontend/static/fonts/",
                        "classpath:/frontend/static/bootstrap/",
                        "classpath:/admin/static/images/",
                        "classpath:/admin/static/img/",
                        "classpath:/admin/static/css/",
                        "classpath:/admin/static/js/",
                        "classpath:/admin/static/fonts/",
                        "classpath:/admin/static/font-awesome/",
                        "classpath:/admin/static/email-templates/",
                        "classpath:/node_modules/"
                );
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(brandFormatter);
        registry.addFormatter(categorySetFormatter);
        registry.addConverter(categoryConverter);
    }


}

