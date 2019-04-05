/*
 * Copyright (c) 2018.
 * Author: Mikhail Sigachev
 */

package com.zource.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Categories")
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonIgnoreProperties( value= {"parentCategories"})
public class Categories {

    @Id
    @Column(name = "category_id", nullable = false)
    @JsonProperty("id")
    private Integer id;

    @Column(name = "Name", length = 255, nullable = false)
    @JsonProperty("name")
    private String name;

    @Column(name = "Description", length = 255, nullable = false)
    private String description;

    @Column(name = "Image", length = 45, nullable = false)
    private String imageFileName;


    @Column(name = "top_banner", length = 45, nullable = false)
    private String topBanner;


    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "Categories_Parents",
            joinColumns = {@JoinColumn(name = "CATEGORY_ID")},
            inverseJoinColumns = {@JoinColumn(name = "PARENT_ID")})

    private Set<Categories> parentCategories = new HashSet<Categories>();


    @JsonIgnore
    @ManyToMany(mappedBy = "parentCategories", fetch = FetchType.EAGER)

    private Set<Categories> subCategories = new HashSet<Categories>();


    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "brand_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Brands brand;


    public Categories() {
    }


}
