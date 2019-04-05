/*
 * Copyright (c) 2018.
 * Author: Mikhail Sigachev
 */

package com.zource.dao;

import com.zource.entity.Categories;
import com.zource.form.CategoryForm;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

import static com.zource.utils.HibernateUtils.loadAllData;

@Transactional
@Repository
public class CategoryDAO {

    @Autowired
    private SessionFactory sessionFactory;


    public List getAllCategories() {
        return loadAllData(Categories.class, sessionFactory.getCurrentSession());
    }

    public Categories getCategoryByID(Integer categoryId) {

        EntityManager em = sessionFactory.getCurrentSession().getEntityManagerFactory().createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Categories> cq = cb.createQuery(Categories.class);
        Root<Categories> prodCat = cq.from(Categories.class);
        Predicate brandNamePredicate = cb.equal(prodCat.get("id"), categoryId);
        cq.where(brandNamePredicate);
        TypedQuery<Categories> query = em.createQuery(cq);

        return (Categories) query.getSingleResult();
    }


    public List getSubCategories(Integer categoryId) {

        Categories cat = this.getCategoryByID(categoryId);
        List list = new ArrayList(cat.getSubCategories());

        return list;
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void save(CategoryForm catdForm) {

        Session session = this.sessionFactory.getCurrentSession();

        Integer id = catdForm.getId();

        System.out.println("##entered save ");

        Categories cat = null;

        boolean isNew = false;
        if (id != null) {
            cat = this.getCategoryByID(id);
        }
        if (cat == null) {
            isNew = true;
            cat = new Categories();
            // cat.setCreateDate(new Date());
        }
        cat.setId(id);

        if (catdForm.getName() != null)
            cat.setName(catdForm.getName());
        if (catdForm.getDescription() != null)
            cat.setDescription(catdForm.getDescription());
        if (catdForm.getImageFileName() != null)
            cat.setImageFileName(catdForm.getImageFileName());


        if (isNew) {
            session.persist(cat);
            System.out.println("## saved " + cat);
        } else {
            session.merge(cat);
            System.out.println("## merged " + cat);
        }
        // If error in DB, Exceptions will be thrown out immediately
        session.flush();
    }


}