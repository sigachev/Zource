/*
 * Copyright (c) 2018.
 * Author: Mikhail Sigachev
 */

package com.zource.dao;

import com.zource.entity.PageMenu;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.zource.utils.HibernateUtils.loadAllData;

@Transactional
@Repository
public class PageMenuDAO {

    @Autowired
    private SessionFactory sessionFactory;


    public List<PageMenu> getAllPageMenus() {

        Session session = this.sessionFactory.getCurrentSession();
        List<PageMenu> menus = (List<PageMenu>) loadAllData(PageMenu.class, session);

        return menus;
    }

    public PageMenu getMenuByID(Integer menuId) {

        PageMenu result = null;
        List<PageMenu> menus = (List<PageMenu>) loadAllData(PageMenu.class, sessionFactory.getCurrentSession());

        for (PageMenu m : menus)
            if (m.getId() == menuId)
                result = m;

        return result;
    }

    public void update(PageMenu m) {

        Session session = this.sessionFactory.getCurrentSession();

        PageMenu menu = null;
        menu.setId(m.getId());
        menu.setTitle(m.getTitle());
        session.merge(menu);

    }

}
