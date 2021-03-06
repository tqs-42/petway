package com.specific.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.specific.dto.CategoryDTO;
import com.specific.exception.ConflictException;
import com.specific.model.Category;
import com.specific.service.CategoryService;

@RestController
@CrossOrigin(origins = {"http://localhost:4201", "http://0.0.0.0:6868" })
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService service;

    @PostMapping("/add")
    public Category addCategory(@RequestBody CategoryDTO category) throws ConflictException {
        return service.saveCategory(category);
    }

    @GetMapping("")
    public List<Category> findAllCategories() {
        return service.getCategories();
    }
}
