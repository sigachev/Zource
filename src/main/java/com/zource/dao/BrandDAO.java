package com.zource.dao;

import com.zource.entity.Brands;
import com.zource.form.BrandForm;
import com.zource.model.BrandInfo;
import com.zource.pagination.PaginationResult;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
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
public class BrandDAO {

    @Autowired
    private SessionFactory sessionFactory;


    public List<Brands> getAllBrands() {

        List<Brands> result = loadAllData(Brands.class, sessionFactory.getCurrentSession());

        return result;
    }


    public Brands getBrandById(Integer id) {
        Brands brand = null;
        EntityManager em = sessionFactory.getCurrentSession().getEntityManagerFactory().createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Brands> cq = cb.createQuery(Brands.class);
        Root<Brands> Brand = cq.from(Brands.class);
        Predicate brandIDPredicate = cb.equal(Brand.get("id"), id);
        cq.where(brandIDPredicate);
        TypedQuery<Brands> query = em.createQuery(cq);

        if (query.getResultList().size() > 0)
            brand = query.getSingleResult();

        return brand;
    }

    public List findBrands(String name, String type) {
        EntityManager em = sessionFactory.getCurrentSession().getEntityManagerFactory().createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Brands> cq = cb.createQuery(Brands.class);

        Root<Brands> brandRoot = cq.from(Brands.class);

        //Constructing list of parameters
        List<Predicate> predicates = new ArrayList<Predicate>();

        predicates.add(cb.like(brandRoot.get("name"), "%" + name + "%"));
        predicates.add(cb.like(brandRoot.get("type"), "%" + type + "%"));


        CriteriaQuery<Brands> query = cq.select(brandRoot).where(predicates.toArray(new Predicate[]{}));
        return em.createQuery(query).getResultList();
    }

    public List getBrandCategories(String id) {
        EntityManager em = sessionFactory.getCurrentSession().getEntityManagerFactory().createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Brands> cq = cb.createQuery(Brands.class);

        Root<Brands> Brand = cq.from(Brands.class);

        Predicate brandNamePredicate = cb.equal(Brand.get("id"), id);
        cq.where(brandNamePredicate);

        TypedQuery<Brands> query = em.createQuery(cq);

        Brands brand = query.getSingleResult();

        List list = new ArrayList(brand.getBrandCategories());

        return list;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void save(BrandForm brandForm) {

        Session session = this.sessionFactory.getCurrentSession();

        Integer id = brandForm.getId();

        System.out.println("##entered save ");

        Brands brand = null;

        boolean isNew = false;
        if (id != null) {
            brand = this.getBrandById(id);
        }
        if (brand == null) {
            isNew = true;
            brand = new Brands();
            // brand.setCreateDate(new Date());
        }
        brand.setId(id);

        if (brandForm.getName() != null)
            brand.setName(brandForm.getName());
        if (brandForm.getType() != null)
            brand.setType(brandForm.getType());
        if (brandForm.getDescription() != null)
            brand.setDescription(brandForm.getDescription());
        if (brandForm.getLogoFileName() != null)
            brand.setLogo(brandForm.getLogoFileName());


        if (isNew) {
            session.persist(brand);
            System.out.println("## saved " + brand);
        } else {
            session.merge(brand);
            System.out.println("## merged " + brand);
        }
        // If error in DB, Exceptions will be thrown out immediately
        session.flush();
    }


    public PaginationResult<BrandInfo> queryBrands(int page, int maxResult, int maxNavigationPage,
                                                   String likeName) {
        String sql = "Select new " + BrandInfo.class.getName() //
                + "(p.id, p.name) " + " from "//
                + Brands.class.getName() + " p ";
        if (likeName != null && likeName.length() > 0) {
            sql += " Where lower(p.name) like :likeName ";
        }
        /*sql += " order by p.createDate desc ";*/
        //
        Session session = this.sessionFactory.getCurrentSession();
        Query<BrandInfo> query = session.createQuery(sql, BrandInfo.class);

        if (likeName != null && likeName.length() > 0) {
            query.setParameter("likeName", "%" + likeName.toLowerCase() + "%");
        }
        return new PaginationResult<BrandInfo>(query, page, maxResult, maxNavigationPage);
    }


}