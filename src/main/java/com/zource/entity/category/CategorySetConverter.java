/*
 * Copyright (c) 2019.
 * Author: Mikhail Sigachev
 */

package com.zource.entity.category;

import com.zource.dao.CategoryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class CategorySetConverter implements Converter<String, Set<Category>> {

    @Autowired
    CategoryDAO categoryDAO;

    @Override
    public Set<Category> convert(String source) {

        Set<Category> result = new HashSet();
        //remove spaces and get separate values
        String[] arr = source.replace(" ", "").split(",");
        Integer id;

        for (String st : arr) {
            id = Integer.parseInt(st);
            result.add(categoryDAO.getById(id));
        }
        return result;
    }
}
