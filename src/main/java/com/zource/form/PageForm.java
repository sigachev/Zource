/*
 * Copyright (c) 2018.
 * Author: Mikhail Sigachev
 */

package com.zource.form;

import com.zource.entity.PageMenu;
import com.zource.entity.Pages;
import lombok.Data;

@Data
public class PageForm {

    private Integer id;
    /* @NotEmpty*/
    private String title;
    private boolean enabled;
    private String metaTitle;
    private String metaKeywords;
    private String metaDescription;
    private String content;
    private String sideContent;
    private PageMenu menu;

    private boolean newPage = false;

    public PageForm() {
        this.newPage = true;
    }

    public PageForm(Pages page) {
        this.id = page.getId();
        this.title = page.getTitle();
        this.enabled = page.isEnabled();
        this.metaTitle = page.getMetaTitle();
        this.metaKeywords = page.getMetaKeywords();
        this.metaDescription = page.getMetaDescription();
        this.content = page.getContent();
        this.sideContent = page.getSideContent();
        this.menu = page.getMenu();
    }


}