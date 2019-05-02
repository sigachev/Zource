/*
 * Copyright (c) 2019.
 * Author: Mikhail Sigachev
 */

package com.zource.controllers.admin.categories.REST;

import com.zource.dao.CategoryDAO;
import com.zource.entity.Category;
import com.zource.model.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class AdminCategoriesRESTController implements AdminCategoriesRESTInterface {


    @Autowired
    private CategoryDAO categoryDAO;

    @Autowired
    Environment env;


    // GET: Category
    @GetMapping("/admin/rest/categoryTree")
    public List<Category> getCategoriesTree(Model model, @RequestParam(value = "id", defaultValue = "0", required = false) Integer id,
                                            @ModelAttribute("info") Info info) {

        System.out.println("Cat id " + id + "parents :" + categoryDAO.getCategoryByID(id).getParentCategories().toString());

        return this.categoryDAO.getAllCategories();


    }

    // GET: Category
    @GetMapping("/admin/rest/getAllCategories")
    public List<Category> getAllCategories() {
/*
        List<Category> list = categoryDAO.getAllCategories();
        for (Category c : list) {
            *//* System.out.println("Cat id = " + c.getId());*//*
            if (c.getParentCategories().size() > 0)
                System.out.println("Parent Id = " + Lists.newArrayList(c.getParentCategories()).get(0));

            System.out.println("**********************");
        }*/

        return categoryDAO.getAllCategories();

    }

    //Ged all categories except for the one with given id
    @GetMapping("/admin/rest/getParentCategoriesOptions/{id}")
    public List<Category> getAllCategories(@PathVariable Integer id) {
        List<Category> result = new ArrayList<>();
        List<Category> allCategories = categoryDAO.getAllCategories();
        for (Category c : allCategories)
            if (c.getId() != id)
                result.add(c);

            return result;
        }


        @GetMapping("/admin/rest/getChildCategoriesIDs")
        public ResponseEntity getChildCategoriesIds (
        @RequestParam(value = "catID", required = false, defaultValue = "0") int id){
            Category category = new Category();

            if ((id != 0) && (id == (int) id))
                category = categoryDAO.getCategoryByID(id);


            return new ResponseEntity(category.getChildrenIDs(), HttpStatus.OK);
        }

        @GetMapping("/admin/rest/getParentCategoriesIds")
        public ResponseEntity getParentCategoriesIds (
        @RequestParam(value = "catID", required = false, defaultValue = "0") int id){
            Category category = new Category();

            if ((id != 0) && (id == (int) id))
                category = categoryDAO.getCategoryByID(id);


            return new ResponseEntity(category.getParentIDs(), HttpStatus.OK);
        }


        @PostMapping("/admin/rest/category/rename/{id}")
        @ResponseStatus(HttpStatus.OK)
        public ResponseEntity<Category> updateCategory (@PathVariable Integer id, @RequestBody String name){

            Category category = this.categoryDAO.getCategoryByID(id);
            System.out.println("Catedory id = " + category.getId() + " ; Category name = " + category.getName());

            if (category != null) {
                category.setName(name);
                System.out.println("Catedory id = " + category.getId() + " ; Category name = " + category.getName());
                this.categoryDAO.update(category);
            }

            return new ResponseEntity<Category>(category, HttpStatus.OK);
        }


        @PostMapping("/admin/rest/category/add/{id}")
        @ResponseStatus(HttpStatus.OK)
        public ResponseEntity<Category> addCategory (@PathVariable Integer parentId, @RequestBody String name){

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
