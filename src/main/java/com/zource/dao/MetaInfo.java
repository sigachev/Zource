/*
 * Copyright (c) 2018.
 * Author: Mikhail Sigachev
 */

package com.zource.dao;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data //generates getters and setters
public class MetaInfo {

    public String title;
    @Setter
    public String desc;
    @Setter(AccessLevel.PROTECTED)
    public String keywords;

    protected void setTitle(String title) {
        this.title = title;
    }

}
