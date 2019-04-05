/*
 * Copyright (c) 2018.
 * Author: Mikhail Sigachev
 */

package com.zource.dao;

import com.zource.entity.Pages;
import com.zource.form.PageForm;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.zource.utils.HibernateUtils.loadAllData;

@Transactional
@Repository
public class PageDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    PageMenuDAO menuDAO;


    public List<Pages> getAllPages() {

        Session session = this.sessionFactory.getCurrentSession();

        List<Pages> pages = (List<Pages>) loadAllData(Pages.class, session);

        return pages;
    }

    public Pages getPageByID(Integer pageId) {

        Pages resultPage = null;
        List<Pages> pages = (List<Pages>) loadAllData(Pages.class, sessionFactory.getCurrentSession());

        for (Pages p : pages)
            if (p.getId() == pageId)
                resultPage = p;

        return resultPage;
    }

    public void save(PageForm pageForm) {

        Session session = this.sessionFactory.getCurrentSession();

        Integer id = pageForm.getId();


        Pages page = null;
        boolean isNew = false;

        if (id != null) {
            page = this.getPageByID(id);
        }
        if (page == null) {
            isNew = true;
            page = new Pages();
        }
        page.setId(id);
        page.setTitle(pageForm.getTitle());
        page.setMetaKeywords(pageForm.getMetaKeywords());
        page.setMetaDescription(pageForm.getMetaDescription());
        page.setMetaTitle(pageForm.getMetaTitle());
        page.setEnabled(pageForm.isEnabled());
        page.setContent(pageForm.getContent());
        page.setSideContent(pageForm.getSideContent());
        page.setMenu(menuDAO.getMenuByID(pageForm.getMenu().getId()));



        /**/
        /*  page.getMenu().setTitle(pageForm.getMenu().getTitle());*/


        if (isNew) {
            session.persist(page);
            System.out.println("## new page saved " + page);
        } else {
            session.merge(page);
            System.out.println("## page merged " + page);
        }
        // If error in DB, Exceptions will be thrown out immediately
        session.flush();
    }


}
