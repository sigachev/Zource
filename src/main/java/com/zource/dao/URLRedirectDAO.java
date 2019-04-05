package com.zource.dao;

import com.zource.entity.URLRedirect;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Transactional
@Repository
public class URLRedirectDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private HibernateTemplate hibernateTemplate;

    public String getSeoURL(String requestURL) {
        EntityManager em = sessionFactory.getCurrentSession().getEntityManagerFactory().createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<URLRedirect> cq = cb.createQuery(URLRedirect.class);
        Root<URLRedirect> urlRedirect = cq.from(URLRedirect.class);
        Predicate predicate = cb.equal(urlRedirect.get("originalURL"), requestURL);
        TypedQuery<URLRedirect> query = em.createQuery(cq.where(predicate));

        List<URLRedirect> res = query.getResultList();

        if (res.isEmpty())
            return "void";
        else
            return res.get(0).getSeoURL();
    }

    @Transactional(readOnly = true)
    public String getOriginalURL(String seoURL) {

        Query query = sessionFactory.getCurrentSession().createQuery("from URLRedirect where seoURL= :seoURL");
        query.setParameter("seoURL", seoURL);

        URLRedirect res = (URLRedirect) query.uniqueResult();
        System.out.println("Res: " + res);
        if (res == null)
            return "void";

        else {
            System.out.println("Original URL : " + res.getOriginalURL());
            return res.getOriginalURL();
        }

    }

}



