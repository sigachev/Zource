/*
 * Copyright (c) 2018.
 * Author: Mikhail Sigachev
 */

package com.zource.form;

import com.zource.entity.Brands;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;

@Data
public class BrandForm {
    private Integer id;

    @NotEmpty
    private String name;
    private String type;
    private String description;
    // Upload files.
    private MultipartFile logoFile;
    private String logoFileName;
    private boolean newBrand = false;

    public BrandForm() {
        this.newBrand = true;
    }

    public BrandForm(Brands brand) {
        this.id = brand.getId();
        this.name = brand.getName();
        this.type = brand.getType();
        this.description = brand.getDescription();
        this.logoFileName = brand.getLogo();
    }

}
