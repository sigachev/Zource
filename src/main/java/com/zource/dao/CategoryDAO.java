/*
 * Copyright (c) 2018.
 * Author: Mikhail Sigachev
 */

package com.zource.dao;

import com.zource.entity.category.Category;
import com.zource.form.CategoryForm;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.zource.utils.HibernateUtils.loadAllData;

@Transactional
@Repository
public class CategoryDAO {

    @Autowired
    private SessionFactory sessionFactory;


    public List<Category> getAllCategories() {
        return loadAllData(Category.class, sessionFactory.getCurrentSession());
    }


    public List<Category> getRootCategories() {
        List<Category> result = new ArrayList();
        List<Category> allCategories = this.getAllCategories();

        for (Category cat : allCategories)
            if (cat.getParentCategories().isEmpty())
                result.add(cat);

        return result;
    }


    public Category getCategoryByID(Integer id) {

        if (id == null)
            return null;

        return sessionFactory.getCurrentSession().get(Category.class, id);

    }


    public Set getChildCategories(Integer categoryId) {

        Category cat = this.getCategoryByID(categoryId);
        Set list = new HashSet();
        if (cat.getChildCategories().toArray() == null) return list;


        return cat.getChildCategories();
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void save(CategoryForm catForm) {

        Session session = this.sessionFactory.getCurrentSession();
        Integer id = catForm.getId();
        Category category = null;

        boolean isNew = false;
        if (id != null)
            category = this.getCategoryByID(id);

        System.out.println("CATEGORY FORM name = " + category.getName());

        if (category == null) {
            isNew = true;
            category = new Category();
        }



        /*   category.setParentCategories(this.getCategoriesByIDs(catForm.getParentIDs()));*/
        category.update(catForm);


        if (isNew) {
            session.persist(category);
            System.out.println("## saved " + category);
        } else {
            session.merge(category);
            System.out.println("## merged " + category);
        }
        // If error in DB, Exceptions will be thrown out immediately
        session.flush();
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void update(Category category) {

        Session session = this.sessionFactory.getCurrentSession();

        Integer id = category.getId();

        Category cat = null;

        session.merge(category);

        session.flush();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void add(Category category) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(category);
    }


    public Set<Category> listParentCategoriesByIDs(Set<Integer> ids) {
        Set<Category> categories = new HashSet<>();
        for (int id : ids)
            categories.add(this.getCategoryByID(id));
        System.out.println("PARENT CATEGORIES: " + categories);

        return categories;
    }

    public Set<Category> getCategoriesByIDs(Set<Integer> IDs) {
        Set<Category> result = new HashSet<>();
        if (!IDs.isEmpty() || IDs != null)
            for (int id : IDs) {
                System.out.println("IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII");
                System.out.println("ID=" + id);
                System.out.println("Category id  :::::: =====  " + this.getCategoryByID(id));
                result.add(this.getCategoryByID(id));
            }

        return result;
    }


}