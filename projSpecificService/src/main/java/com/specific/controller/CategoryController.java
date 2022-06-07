package com.specific.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.specific.model.Category;
import com.specific.service.CategoryService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService service;

    @PostMapping("/add-category")
    public Category addCategory(@RequestBody Category category) {
        return service.saveCategory(category);
    }

    @PostMapping("/addCategories")
    public List<Category> addCategories(@RequestBody List<Category> categories) {
        return service.saveCategories(categories);
    }

    @GetMapping("/categories")
    public List<Category> findAllCategories() {
        return service.getCategories();
    }

    @GetMapping("/categoryById/{id}")
    public Category findCategoryById(@PathVariable int id) {
        return service.getCategoryById(id);
    }

    @PutMapping("/updateCategory")
    public Category updateCategory(@RequestBody Category category) {
        return service.updateCategory(category);
    }

    @DeleteMapping("/deleteCategory/{id}")
    public String deleteCategory(@PathVariable int id) {
        return service.deleteCategory(id);
    }
}
