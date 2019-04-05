package com.zource.dao;

import com.zource.entity.Products;
import com.zource.form.ProductForm;
import com.zource.model.ProductInfo;
import com.zource.pagination.PaginationResult;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static com.zource.utils.HibernateUtils.loadAllData;

@Transactional
@Repository
public class ProductDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public List<Products> getAllProducts() {
        return loadAllData(Products.class, sessionFactory.getCurrentSession());
    }

    public Products getProductById(Integer id) {

        return sessionFactory.getCurrentSession().getEntityManagerFactory().createEntityManager().getReference(Products.class, id);

    }

    public ProductInfo findProductInfo(Integer id) {
        Products product = this.getProductById(id);
        if (product == null) {
            return null;
        }
        return new ProductInfo(product.getId(), product.getName(), product.getPrice());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void save(ProductForm productForm) {

        Session session = this.sessionFactory.getCurrentSession();
        Integer id = productForm.getId();

        Products product = null;

        boolean isNew = false;
        if (id != null) {
            product = this.getProductById(id);
        }
        if (product == null) {
            isNew = true;
            product = new Products();
            product.setCreateDate(new Date());
        }
        product.setId(id);
        product.setName(productForm.getName());
        product.setPrice(productForm.getPrice());


        if (isNew) {
            session.persist(product);
        } else session.merge(product);
        // If error in DB, Exceptions will be thrown out immediately
        session.flush();
    }


    public PaginationResult<ProductInfo> queryProducts(int page, int maxResult, int maxNavigationPage,
                                                       String likeName) {
        String sql = "Select new " + ProductInfo.class.getName() //
                + "(p.code, p.name, p.price) " + " from "//
                + Products.class.getName() + " p ";
        if (likeName != null && likeName.length() > 0) {
            sql += " Where lower(p.name) like :likeName ";
        }
        sql += " order by p.createDate desc ";
        //
        Session session = this.sessionFactory.getCurrentSession();
        Query<ProductInfo> query = session.createQuery(sql, ProductInfo.class);

        if (likeName != null && likeName.length() > 0) {
            query.setParameter("likeName", "%" + likeName.toLowerCase() + "%");
        }
        return new PaginationResult<ProductInfo>(query, page, maxResult, maxNavigationPage);
    }

    public PaginationResult<ProductInfo> queryProducts(int page, int maxResult, int maxNavigationPage) {
        return queryProducts(page, maxResult, maxNavigationPage, null);
    }

}