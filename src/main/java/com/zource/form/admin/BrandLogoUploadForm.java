/*
 * Copyright (c) 2018.
 * Author: Mikhail Sigachev
 */

package com.zource.form.admin;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
public class BrandLogoUploadForm {

    private int brandId;

    private String description;

    // Upload files.
    private MultipartFile[] fileDatas;


}