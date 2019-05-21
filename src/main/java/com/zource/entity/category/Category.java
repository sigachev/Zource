/*
 * Copyright (c) 2019.
 * Author: Mikhail Sigachev
 */

package com.zource.entity.category;

import com.fasterxml.jackson.annotation.*;
import com.zource.entity.Brand;
import com.zource.entity.product.Product;
import com.zource.form.admin.category.CategoryForm;
import com.zource.form.admin.category.CategoryLogoForm;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
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


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "parent_child_category",
            joinColumns = @JoinColumn(name = "child_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "parent_id", nullable = false))
    @JsonProperty("parents")
    @JsonView(CategoryViews.ParentsView.class)
    private Set<Category> parentCategories = new HashSet<>();


    @ManyToMany(mappedBy = "parentCategories", fetch = FetchType.LAZY)
    @JsonProperty("children")
    @JsonView(CategoryViews.ChildrenView.class)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<Category> childCategories = new HashSet<>();

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "brand_id")
    private Brand brand = new Brand();

    @JsonIgnore
    @ManyToMany(mappedBy = "categories", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Product> products = new HashSet<>();

    public Category() {
    }

    public void update(CategoryForm catForm) {

        //do not set id, otherwise new row in DB will be created
        this.setName(catForm.getName());
        this.setDescription(catForm.getDescription());
        this.setBrand(catForm.getBrand());
        //update parent categories
        this.setParentCategories(catForm.getParentCategories());
    }

    public void update(CategoryLogoForm logoForm) {
        this.setLogoFileName(logoForm.getLogoFileName());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;
        Category category = (Category) o;
        return Objects.equals(getId(), category.getId()) &&
                Objects.equals(getName(), category.getName()) &&
                Objects.equals(getDescription(), category.getDescription()) &&
                Objects.equals(getLogoFileName(), category.getLogoFileName()) &&
                Objects.equals(getTopBanner(), category.getTopBanner()) &&
                Objects.equals(getBrand(), category.getBrand());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), getLogoFileName(), getTopBanner(), getBrand());
    }
}
