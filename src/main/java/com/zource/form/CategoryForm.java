/*
 * Copyright (c) 2018.
 * Author: Mikhail Sigachev
 */

package com.zource.form;

import com.zource.entity.Categories;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class CategoryForm {
    private Integer id;

    @NotEmpty
    private String name;
    @Size(min=10, max = 499)
    private String description;
    private MultipartFile imageFile;
    private String imageFileName;
    private boolean newCategory = false;

    public CategoryForm() {
        this.newCategory = true;
    }

    public CategoryForm(Categories cat) {
        this.id = cat.getId();
        this.name = cat.getName();
        this.description = cat.getDescription();
        this.imageFileName = cat.getImageFileName();

    }
}
