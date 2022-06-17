package com.specific.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.specific.model.Category;
import com.specific.repository.CategoryRepository;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository repository;

    public Category saveCategory(Category category) {
        return repository.save(category);
    }
}
