/*
 * Copyright (c) 2019.
 * Author: Mikhail Sigachev
 */

package com.zource.services;

import com.zource.entity.category.Category;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface CategoryService {

    List<Category> getAllCategories();

    List<Category> getRootCategories();

    Category getById(Integer id);

    /* Set<Category> getChildCategories(Integer categoryId);*/

    /*  void save(CategoryForm catForm);*/

    void update(Category category);

    void add(Category category);
}
