package com.zource.dao;

import com.zource.entity.product.Product;
import com.zource.form.ProductForm;
import com.zource.model.ProductInfo;
import com.zource.pagination.PaginationResult;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static com.zource.utils.HibernateUtils.loadAllData;

@Transactional
@Repository
public class ProductDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public List<Product> getAllProducts() {

        List<Product> result = loadAllData(Product.class, sessionFactory.getCurrentSession());
        Initialize(result);

        return result;
    }


    public Product getById(Integer id) {
        Product result = sessionFactory.getCurrentSession().get(Product.class, id);
        if (result != null)
            Initialize(result);
        return result;

    }

    public ProductInfo findProductInfo(Integer id) {
        Product product = this.getById(id);
        if (product == null) {
            return null;
        }
        return new ProductInfo(product.getId(), product.getName(), product.getPrice());
    }


    // MANDATORY: Transaction must be created before.
    @Transactional(propagation = Propagation.REQUIRED)
    public void save(ProductForm productForm) {

        Session session = this.sessionFactory.getCurrentSession();
        Integer id = productForm.getId();
        Product product = new Product();
        boolean isNew = false;

        if (id != null)
            product = this.getById(id);
        else {
            System.out.println("PRODUCT NULL");
            isNew = true;
        }

        product.update(productForm);


        if (isNew) {
            System.out.println("PRODUCT PERSISTED");
            session.persist(product);
        } else {
            System.out.println("PRODUCT MERGED: id " + product.getId());
            session.merge(product);
        }
        session.flush();
    }


    public PaginationResult<ProductInfo> queryProducts(int page, int maxResult, int maxNavigationPage,
                                                       String likeName) {
        String sql = "Select new " + ProductInfo.class.getName() //
                + "(p.code, p.name, p.price) " + " from "//
                + Product.class.getName() + " p ";
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


    public void Initialize(Product product) {
        Hibernate.initialize(product.getCategories());
        Hibernate.initialize(product.getBrand());
    }

    public void Initialize(Set<Product> set) {
        for (Product product : set)
            this.Initialize(product);
    }

    public void Initialize(List<Product> list) {
        for (Product product : list)
            this.Initialize(product);
    }

}