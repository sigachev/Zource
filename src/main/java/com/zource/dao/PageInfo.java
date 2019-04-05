/*
 * Copyright (c) 2018.
 * Author: Mikhail Sigachev
 */

package com.zource.dao;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data //generates getters and setters
public class PageInfo {

    public String title;
    @Setter
    public String metaTitle;
    @Setter
    public String metaDesc;
    @Setter(AccessLevel.PROTECTED)
    public String metaKeywords;

    protected void setTitle(String title) {
        this.title = title;
    }

}
