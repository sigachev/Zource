package com.zource.form;

import com.zource.entity.Products;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductForm {
    private Integer id;
    private String SKU;
    private String name;
    private String description;
    private double price;
    private boolean enabled;

    private boolean newProduct = false;

    // Upload file.
    private MultipartFile fileData;

    public ProductForm() {
        this.newProduct = true;
    }

    public ProductForm(Products product) {
        this.id = product.getId();
        this.SKU = product.getSKU();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.enabled = product.isEnabled();
    }


}