/*
 * Copyright (c) 2018.
 * Author: Mikhail Sigachev
 */

package com.zource.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "URL_Redirect")
@Getter
@Setter
public class URLRedirect {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "url_redirect_id", length = 11, nullable = false)
    private Integer id;


    @Column(name = "original_url", length = 255, nullable = false)
    private String originalURL;

    @Column(name = "seo_url", length = 255, nullable = false)
    private String seoURL;


    public URLRedirect() {
    }
}
