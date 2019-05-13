/*
 * Copyright (c) 2019.
 * Author: Mikhail Sigachev
 */

package com.zource.controllers.admin.brands;

import com.zource.dao.BrandDAO;
import com.zource.entity.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Locale;

@Service
public class BrandFormatter implements Formatter<Brand> {

    @Autowired
    BrandDAO brandDAO;

    @Override
    public Brand parse(String text, Locale locale) throws ParseException {
        Integer id = Integer.parseInt(text);
        return this.brandDAO.getBrandById(id);//return Type object form DB
    }

    @Override
    public String print(Brand object, Locale locale) {
        return (object != null ? object.getId().toString() : "");
    }


}

