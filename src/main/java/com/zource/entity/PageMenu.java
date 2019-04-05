/*
 * Copyright (c) 2018.
 * Author: Mikhail Sigachev
 */

package com.zource.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Table(name = "page_menus")
@Getter
@Setter
public class PageMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 11, nullable = false)
    private Integer id;

    @NotEmpty
    @Column(name = "name")
    private String name;


    @NotEmpty
    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Pages> pages;


    public PageMenu() {
    }


}
