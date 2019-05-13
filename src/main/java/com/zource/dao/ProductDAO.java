package com.zource.dao;

import com.zource.entity.Product;
import com.zource.form.ProductForm;
import com.zource.model.ProductInfo;
import com.zource.pagination.PaginationResult;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.Date;
import java.util.List;

import static com.zource.utils.HibernateUtils.loadAllData;

@Transactional
@Repository
@PersistenceContext(type = PersistenceContextType.EXTENDED)
public class ProductDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public List<Product> getAllProducts() {
        return loadAllData(Product.class, sessionFactory.getCurrentSession());
    }

    public Product getProductById(Integer id) {
        Product result = sessionFactory.getCurrentSession().get(Product.class, id);

        return result;

    }

    public ProductInfo findProductInfo(Integer id) {
        Product product = this.getProductById(id);
        if (product == null) {
            return null;
        }
        return new ProductInfo(product.getId(), product.getName(), product.getPrice());
    }

    @Transactional
    public void save(ProductForm productForm) {

        Session session = this.sessionFactory.getCurrentSession();
        Integer id = productForm.getId();

        Product product = null;

        boolean isNew = false;
        if (id != null)
            product = this.getProductById(id);

        System.out.println("PRODUCT name = " + product.getName());


        if (product == null) {

            System.out.println("PRODUCT NULL");
            isNew = true;
            product = new Product();
            product.setCreateDate(new Date());
        }

        System.out.println("AFTER PRODUCT NULL");
        product.setName(productForm.getName());
        product.setSKU(productForm.getSKU());
        product.setEnabled(productForm.isEnabled());

        System.out.println("Brand::::  " + productForm.getBrand().getId());
        product.setBrand(productForm.getBrand());
        product.setPrice(productForm.getPrice());
        product.setDescription(productForm.getDescription());

        product.setCreateDate(new Date());

        product.setCategories(productForm.getCategories());


        if (isNew) {
            System.out.println("PRODUCT PERSIST");
            session.persist(product);
        } else {
            System.out.println("PRODUCT MERGED: id " + product.getId());
            session.merge(product);
        }
        // If error in DB, Exceptions will be thrown out immediately
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

}