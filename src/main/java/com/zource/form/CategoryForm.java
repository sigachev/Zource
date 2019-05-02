/*
 * Copyright (c) 2018.
 * Author: Mikhail Sigachev
 */

package com.zource.form;

import com.zource.dao.CategoryDAO;
import com.zource.entity.Category;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
/*    private Set<Category> childrenCategories;
    private Set<Category> parentCategories;*/

    private Set<Integer> parentIDs;

    private String[] multiCheckboxSelectedValues;

    public CategoryForm() {
        this.newCategory = true;
    }

    public CategoryForm(Category cat) {
        this.id = cat.getId();
        this.name = cat.getName();
        this.description = cat.getDescription();
        this.logoFileName = cat.getLogoFileName();
/*        this.parentCategories = cat.getParentCategories();
        this.childrenCategories = cat.getChildCategories();*/

    }


    public void setCategory(Category cat) {
        this.id = cat.getId();
        this.name = cat.getName();
        this.description = cat.getDescription();
        this.logoFileName = cat.getLogoFileName();
/*        this.parentCategories = cat.getParentCategories();
        this.childrenCategories = cat.getChildCategories();*/

    }

    public void setParentIDs(Set<Integer> parentIDs) {
        this.parentIDs = parentIDs;

    }



}
