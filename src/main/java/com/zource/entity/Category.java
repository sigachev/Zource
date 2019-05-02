/*
 * Copyright (c) 2018.
 * Author: Mikhail Sigachev
 */

package com.zource.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.zource.form.CategoryForm;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Categories")
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "text"})
//@JsonIgnoreProperties( value= {"parentCategories"})
public class Category {

    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Integer id;

    @Column(name = "name", length = 255)
    @JsonProperty("text")
    private String name;

    @JsonIgnore
    @Column(name = "description", length = 255)
    private String description;

    @JsonIgnore
    @Column(name = "image", length = 100)
    private String logoFileName;

    @JsonIgnore
    @Column(name = "top_banner", length = 100)
    private String topBanner;

    public void setParentCategories(Set<Category> parentCategories) {
        this.parentCategories = parentCategories;
    }

    @JsonProperty("parents")
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "parent_child_category",
            joinColumns = @JoinColumn(name = "child_id"),
            inverseJoinColumns = @JoinColumn(name = "parent_id"))
    private Set<Category> parentCategories = new HashSet<Category>();

    @JsonIgnore
    @ManyToMany(mappedBy = "parentCategories", fetch = FetchType.EAGER)
    private Set<Category> childCategories = new HashSet<Category>();

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    public Category() {
    }

    public void update(CategoryForm catForm) {


           //do not set id, otherwise new row in DB will be created
            this.setName(catForm.getName());
            this.setDescription(catForm.getDescription());
            this.setLogoFileName(catForm.getLogoFileName());

        //update parent categories

    }


    public Set<Integer> getChildrenIDs() {
        Set result = new HashSet();
        for (Category cat : this.getChildCategories()) {
            result.add(cat.getId());
        }
        return result;
    }

    public Set<Integer> getParentIDs() {
        Set result = new HashSet();
        for (Category cat : this.getParentCategories()) {
            result.add(cat.getId());
        }
        return result;
    }
}
