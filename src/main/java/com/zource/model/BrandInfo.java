package com.zource.model;

import com.zource.entity.Brands;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrandInfo {
    private Integer id;
    private String name;
    private String type;
    private String description;

    public BrandInfo() {
    }

    public BrandInfo(Brands brand) {
        this.id = brand.getId();
        this.type = brand.getType();
        this.description = brand.getDescription();
    }

    // Using in JPA/Hibernate query
    public BrandInfo(Integer id, String name, String type, String description) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
    }


}