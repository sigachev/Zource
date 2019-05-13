/*
 * Copyright (c) 2019.
 * Author: Mikhail Sigachev
 */

package com.zource.controllers.admin.categories.REST;

import com.fasterxml.jackson.annotation.JsonView;
import com.zource.dao.CategoryDAO;
import com.zource.entity.category.Category;
import com.zource.entity.category.CategoryViews;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/admin/rest/categories")
public class AdminRESTCategoriesController {


    @Autowired
    Environment env;
    @Autowired
    private CategoryDAO categoryDAO;


    // GET: Category
/*    @GetMapping("/categoryTree")
    public List<Category> getCategoriesTree(Model model, @RequestParam(value = "id", defaultValue = "0", required = false) Integer id,
                                            @ModelAttribute("info") Info info) {

        System.out.println("Cat id " + id + "parents :" + categoryDAO.getCategoryByID(id).getParentCategories().toString());

        return this.categoryDAO.getAllCategories();


    }*/

    // GET: Category
    @GetMapping("/getAllCategories")
    @JsonView(CategoryViews.ChildrenView.class)
    public ResponseEntity<List> getAllCategories() {
        return new ResponseEntity(categoryDAO.getAllCategories(), HttpStatus.OK);
    }

    // GET Root Categories
    @GetMapping("/getRootCategories")
    @JsonView(CategoryViews.ChildrenView.class)
    public ResponseEntity<List> getRootCategories() {
        return new ResponseEntity(categoryDAO.getRootCategories(), HttpStatus.OK);
    }

    //Ged all categories except for the one with given id
    @GetMapping("/getParentCategoriesOptions/{id}")
    @JsonView(CategoryViews.ChildrenView.class) // get JSON: id, name, children category
    public ResponseEntity getParentCategoriesOptions(@PathVariable Integer id) {
        List<Category> result = new ArrayList<>();
        List<Category> allCategories = categoryDAO.getAllCategories();
        for (Category c : allCategories)
            if (c.getId() != id)
                result.add(c);

        return new ResponseEntity(result, HttpStatus.OK);
    }


    @JsonView(CategoryViews.ChildrenView.class)
    @GetMapping("/getChildCategories")
    public ResponseEntity getChildCategoriesIDs(
            @RequestParam(value = "catID", required = false, defaultValue = "0") int id) {
        Category category = new Category();

        if ((id != 0))
            category = categoryDAO.getCategoryByID(id);


        return new ResponseEntity(category.getChildCategories(), HttpStatus.OK);
    }


    @JsonView(CategoryViews.ParentsView.class)
    @GetMapping("/getParentCategories")
    public ResponseEntity getParentCategoriesIDs(
            @RequestParam(value = "catID", required = false, defaultValue = "0") int id) {
        Category category = new Category();

        if ((id != 0) && (id == (int) id))
            category = categoryDAO.getCategoryByID(id);


        return new ResponseEntity(category.getParentCategories(), HttpStatus.OK);
    }


    @PostMapping("/category/rename/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Category> updateCategory(@PathVariable Integer id, @RequestBody String name) {

        Category category = this.categoryDAO.getCategoryByID(id);
        System.out.println("Catedory id = " + category.getId() + " ; Category name = " + category.getName());

        if (category != null) {
            category.setName(name);
            System.out.println("Catedory id = " + category.getId() + " ; Category name = " + category.getName());
            this.categoryDAO.update(category);
        }

        return new ResponseEntity<Category>(category, HttpStatus.OK);
    }


    @PostMapping("/category/add/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Category> addCategory(@PathVariable Integer parentId, @RequestBody String name) {

        Category parentCategory = this.categoryDAO.getCategoryByID(parentId);
        Category cat = null;
        if (parentCategory != null) {
            cat = new Category();
            Set<Category> parentCategories = new HashSet<>();
            parentCategories.add(parentCategory);
            cat.setParentCategories(parentCategories);
            cat.setName(name);
            this.categoryDAO.add(cat);
        }

        return new ResponseEntity<Category>(cat, HttpStatus.OK);
    }
}
