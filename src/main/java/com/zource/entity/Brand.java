package com.zource.entity;


import com.zource.entity.category.Category;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Brands")
@Getter
@Setter
public class Brand implements Serializable {

    private static final long serialVersionUID = -1000119078147252957L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 11, nullable = false)
    private Integer id;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "logo")
    private String logo;

    @Column(name = "type")
    private String type;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "brand", fetch = FetchType.EAGER)
    private Set<Product> products = new HashSet<>();

    @OneToMany(mappedBy = "brand")
    private Set<Category> brandCategories = new HashSet<>();


    public Brand() {
    }


}