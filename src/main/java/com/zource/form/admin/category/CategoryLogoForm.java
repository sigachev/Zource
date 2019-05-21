/*
 * Copyright (c) 2019.
 * Author: Mikhail Sigachev
 */

package com.zource.form.admin.category;

import com.zource.entity.category.Category;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CategoryLogoForm {

    private Integer id;
    private String logoFileName;
    private MultipartFile logoFile;


    public void updateLogo(Category category) {
        this.logoFileName = category.getLogoFileName();
    }


}
