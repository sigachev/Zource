/*
 * Copyright (c) 2019.
 * Author: Mikhail Sigachev
 */

package com.zource.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Data
@NoArgsConstructor
public class CategoryTreeModel {

    @JsonProperty
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @JsonProperty
    String name;

}
