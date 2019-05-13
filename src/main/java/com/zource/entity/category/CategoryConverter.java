/*
 * Copyright (c) 2019.
 * Author: Mikhail Sigachev
 */

package com.zource.entity.category;

import com.zource.dao.CategoryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoryConverter implements Converter<String, Category> {

    @Autowired
    CategoryDAO categoryDAO;

    @Override
    public Category convert(String source) {

        return categoryDAO.getCategoryByID(Integer.parseInt(source));

    }

}
