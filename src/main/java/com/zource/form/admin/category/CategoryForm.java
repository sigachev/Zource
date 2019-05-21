/*
 * Copyright (c) 2019.
 * Author: Mikhail Sigachev
 */

package com.zource.form.admin.category;

import com.zource.dao.CategoryDAO;
import com.zource.entity.Brand;
import com.zource.entity.category.Category;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
public class CategoryForm {

    @Autowired
    private CategoryDAO categoryDAO;


    private Integer id;
    @NotEmpty
    private String name;
    @Size(min = 10, max = 499)
    private String description;
    private MultipartFile logoFile;
    private String logoFileName;
    private boolean newCategory = false;
    private Set<Category> parentCategories = new HashSet<>();
    private Brand brand;


    public CategoryForm() {
        this.newCategory = true;
    }

    public CategoryForm(Category cat) {
        this.id = cat.getId();
        this.name = cat.getName();
        this.description = cat.getDescription();
        this.logoFileName = cat.getLogoFileName();
        this.parentCategories = cat.getParentCategories();
        this.brand = cat.getBrand();
    }


    public void update(Category cat) {
        this.id = cat.getId();
        this.parentCategories = cat.getParentCategories();
        this.name = cat.getName();
        this.description = cat.getDescription();
        this.brand = cat.getBrand();
        this.logoFileName = cat.getLogoFileName();

    }

/*    public void updateMainInfo(CategoryForm categoryForm) {

        this.setId(categoryForm.getId());
        this.setParentCategories(categoryForm.getParentCategories());
        this.setName(categoryForm.getName());
        this.setDescription(categoryForm.getDescription());
        this.setBrand(categoryForm.getBrand());

    }*/

    public void updateImages(CategoryForm categoryForm) {

        this.logoFileName = categoryForm.getLogoFileName();
    }

}
