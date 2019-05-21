/*
 * Copyright (c) 2018.
 * Author: Mikhail Sigachev
 */

package com.zource.dao;

import com.zource.entity.category.Category;
import com.zource.services.CategoryService;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.zource.utils.HibernateUtils.loadAllData;

@Transactional
@Repository
public class CategoryDAO implements CategoryService {

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    @Autowired
    private SessionFactory sessionFactory;


    public List<Category> getAllCategories() {
        List<Category> list = loadAllData(Category.class, sessionFactory.getCurrentSession());
        Initialize(list);
        return list;
    }


    public List<Category> getRootCategories() {
        List<Category> result = new ArrayList();
        List<Category> allCategories = this.getAllCategories();

        for (Category cat : allCategories)
            if (cat.getParentCategories().isEmpty())
                result.add(cat);

        Initialize(result);

        return result;
    }

    public Category getById(Integer id) {

        if (id == null)
            return null;

        Category category = sessionFactory.getCurrentSession().get(Category.class, id);
        Initialize(category);
        return category;

    }


/*    public Set getChildCategories(Integer categoryId) {

        Category cat = this.getById(categoryId);
        Set list = new HashSet();
        if (cat.getChildCategories().toArray() == null) return list;

        return cat.getChildCategories();
    }*/

    //Recursively creates list of all parents for this category
    public List getRecursiveParentsList(Category category) {
        List result = new ArrayList();
        if (category == null)
            return result;

        if (category.getParentCategories() != null)
            for (Category c : category.getParentCategories()) {
                sessionFactory.getCurrentSession().update(c);
                result.add(c.getId());

                result.addAll(getRecursiveParentsList(c));
            }
        return result;
    }

    //Recursively creates list of all children for this category
    public List getRecursiveChildrenList(Category category) {
        List result = new ArrayList();
        if (category == null)
            return result;

        if (category.getChildCategories() != null)
            for (Category c : category.getChildCategories()) {
                sessionFactory.getCurrentSession().update(c);
                result.add(c.getId());

                result.addAll(getRecursiveChildrenList(c));
            }
        return result;
    }


    //Check recursively if any of the parent categories has this category as it's parent
    //@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean isParentLoop(Category mainCategory, Category newCategory) {

        System.out.println("RECURSIVE CHILDREN Categories: " + getRecursiveChildrenList(mainCategory));

        if (getRecursiveChildrenList(mainCategory).contains(newCategory.getId()))
            return true;
        else return false;

    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void update(Category category) {

        Session session = this.sessionFactory.getCurrentSession();

        session.merge(category);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void add(Category category) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(category);
    }


    private void Initialize(Category category) {
        if (category.getParentCategories() != null)
            Hibernate.initialize(category.getParentCategories());
        if (category.getChildCategories() != null)
            Hibernate.initialize(category.getChildCategories());
        if (category.getProducts() != null)
            Hibernate.initialize(category.getProducts());
        if (category.getBrand() != null)
            Hibernate.initialize(category.getBrand());
    }

    private void Initialize(List<Category> list) {
        for (Category category : list)
            this.Initialize(category);
    }

    private void Initialize(Set<Category> set) {
        for (Category category : set)
            this.Initialize(category);
    }


}