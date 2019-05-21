package com.zource.model;

import com.zource.entity.product.Product;
import lombok.Data;

@Data
public class ProductInfo {
    private Integer id;
    private String name;
    private double price;

    public ProductInfo() {
    }

    public ProductInfo(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
    }

    // Using in JPA/Hibernate query
    public ProductInfo(Integer id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

}
