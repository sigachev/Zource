/*
 * Copyright (c) 2019.
 * Author: Mikhail Sigachev
 */

package com.zource.entity.category;

import com.zource.dao.CategoryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.*;

@Component
@Transactional
public class CategorySetFormatter implements Formatter<Set<Category>> {

    @Autowired
    CategoryDAO categoryDAO;

    @Override
    public Set<Category> parse(String text, Locale locale) throws ParseException {

        Set<Category> result = new HashSet();

        //remove spaces and get separate values
        String[] arr = text.replace(" ", "").split(",");
        Integer id;

        for (String st : arr) {
            id = Integer.parseInt(st);
            result.add(categoryDAO.getById(id));
            System.out.println("Cat id::: " + categoryDAO.getById(id).getId());
        }


        return result;
    }

    @Override
    public String print(Set<Category> object, Locale locale) {

        List arr = new ArrayList();

        if (object == null)
            return "";
        else
            for (Category c : object)
                arr.add(c.getId().toString());

        String result = String.join(",", arr);

        return result;
    }
}
