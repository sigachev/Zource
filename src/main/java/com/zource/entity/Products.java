package com.zource.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name = "Products")
public class Products implements Serializable {

    private static final long serialVersionUID = -1000119078147252957L;

    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "sku")
    private String SKU;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private double price;

    @Column(name = "image")
    String image;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    private Brands brand;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", nullable = false)
    private Date createDate;

    @Column(name = "enabled")
    private boolean enabled;


    public Products() {
    }


}