/*
 * Copyright (c) 2018.
 * Author: Mikhail Sigachev
 */

package com.zource.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "Pages")
@Getter
@Setter
public class Pages {

    private static final long serialVersionUID = -1000119078147252957L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "page_id", length = 11, nullable = false)
    private Integer id;

    @NotEmpty
    @Column(name = "title", length = 255)
    private String title;

    @Column(name = "enabled")
    private boolean Enabled;

    @Column(name = "metaTitle", length = 255)
    private String metaTitle;

    @Column(name = "metaKeywords", length = 255)
    private String metaKeywords;

    @Column(name = "metaDescription", length = 255)
    private String metaDescription;

    @Column(name = "html_content")
    private String content;

    @Column(name = "side_html")
    private String sideContent;


    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "menu_id", referencedColumnName = "id")
    private PageMenu menu = new PageMenu();


    public Pages() {
    }

}
