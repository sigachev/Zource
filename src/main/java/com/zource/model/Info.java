/*
 * Copyright (c) 2018.
 * Author: Mikhail Sigachev
 */

package com.zource.model;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.zource.entity.Pages;
import com.zource.entity.Products;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Info {

    @Autowired
    Environment env;

    String title;
    String metaTitle;
    String metaDescription;
    String metaKeywords;
    BiMap<String, String> breadcrumb;
    List<String> cssList;

    List<String> topJsList;
    List<String> bottomJsList;


    public Info(String type) {

        /*type = "";*/
        this.title = "";
        this.metaTitle = "";
        this.metaKeywords = "";
        this.cssList = new ArrayList();
        this.topJsList = new ArrayList();
        this.bottomJsList = new ArrayList();

        this.breadcrumb = HashBiMap.create();
        if (type.equalsIgnoreCase("admin")) {
            this.getBreadcrumb().put("Home", "/admin");
        } else {
            this.getBreadcrumb().put("Home", "/");
        }
    }

    public void setInfo(Pages page) {

        this.title = page.getTitle();
        this.metaTitle = page.getMetaTitle();
        this.metaKeywords = page.getMetaKeywords();
        this.metaDescription = page.getMetaDescription();
    }


    public void setInfo(Products prod) {

        /*this.title = prod.getTitle();
        this.metaTitle = page.getMetaTitle();
        this.metaKeywords = page.getMetaKeywords();
        this.metaDescription = page.getMetaDescription();*/
    }


}
