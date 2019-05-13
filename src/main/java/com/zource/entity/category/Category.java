/*
 * Copyright (c) 2019.
 * Author: Mikhail Sigachev
 */

package com.zource.entity.category;

import com.fasterxml.jackson.annotation.*;
import com.zource.entity.Brand;
import com.zource.entity.Product;
import com.zource.form.CategoryForm;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Categories")
@Getter
@Setter
@JsonPropertyOrder({"id", "text"})
//@JsonIgnoreProperties( value= {"parentCategories"})
public class Category {

    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    @JsonView(CategoryViews.ShortView.class)
    private Integer id;

    @Column(name = "name", length = 255)
    @JsonProperty("name")
    @JsonView(CategoryViews.ShortView.class)
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


    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "parent_child_category",
            joinColumns = @JoinColumn(name = "child_id"),
            inverseJoinColumns = @JoinColumn(name = "parent_id"))
    @JsonProperty("parents")
    @JsonView(CategoryViews.ParentsView.class)

    private Set<Category> parentCategories = new HashSet<Category>();


    @ManyToMany(mappedBy = "parentCategories", fetch = FetchType.EAGER)
    @JsonProperty("children")
    @JsonView(CategoryViews.ChildrenView.class)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<Category> childCategories = new HashSet<Category>();

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @JsonIgnore
    @ManyToMany(mappedBy = "categories")
    private Set<Product> products = new HashSet<>();

    public Category() {
    }

    public void update(CategoryForm catForm) {


        //do not set id, otherwise new row in DB will be created
        this.setName(catForm.getName());
        this.setDescription(catForm.getDescription());
        this.setLogoFileName(catForm.getLogoFileName());

        //update parent categories
        this.setParentCategories(catForm.getParentCategories());
    }


/*    public Set<Integer> getChildrenIDs() {
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
    }*/
}
