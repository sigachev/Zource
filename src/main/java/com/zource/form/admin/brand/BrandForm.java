/*
 * Copyright (c) 2019.
 * Author: Mikhail Sigachev
 */

package com.zource.form.admin.brand;

import com.zource.entity.Brand;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;

@Data
public class BrandForm {
    private Integer id;

    @NotEmpty
    private String name;
    private String type;
    @NotEmpty
    private String description;
    // Upload files.
    private MultipartFile logoFile;
    private String logoFileName;
    private boolean newBrand = false;

    public BrandForm() {
        this.newBrand = true;
    }

    public BrandForm(Brand brand) {
        this.id = brand.getId();
        this.name = brand.getName();
        this.type = brand.getType();
        this.description = brand.getDescription();
        this.logoFileName = brand.getLogo();
    }

    public void setBrand(Brand brand) {
        this.id = brand.getId();
        this.name = brand.getName();
        this.type = brand.getType();
        this.description = brand.getDescription();
        this.logoFileName = brand.getLogo();
    }

}
