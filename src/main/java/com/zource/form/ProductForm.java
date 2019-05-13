package com.zource.form;

import com.zource.entity.Brand;
import com.zource.entity.Product;
import com.zource.entity.category.Category;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Data
public class ProductForm {
    private Integer id;
    private String SKU;
    private String name;
    private String description;
    private double price;
    private boolean enabled = true;
    private Set<Category> categories;
    private Brand brand;

    private boolean newProduct = false;

    // Upload file.
    private MultipartFile fileData;

    public ProductForm() {
        this.newProduct = true;
    }

    public ProductForm(Product product) {

        this.id = product.getId();
        this.name = product.getName();
        this.SKU = product.getSKU();
        this.enabled = product.isEnabled();
        this.brand = product.getBrand();
        this.price = product.getPrice();
        this.description = product.getDescription();
    }


}