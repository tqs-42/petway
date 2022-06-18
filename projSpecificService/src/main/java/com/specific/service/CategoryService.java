package com.specific.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.specific.exception.ConflictException;
import com.specific.model.Category;
import com.specific.repository.CategoryRepository;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository repository;

    public Category saveCategory(Category category) throws ConflictException {
        List<Category> categories = getCategories();
        for (Category category2 : categories) {
            if (category2.getName().toLowerCase().equals(category.getName().toLowerCase())) {
                throw new ConflictException("Category already exists");
            }
        }
        return repository.save(category);
    }

    public List<Category> getCategories() {
        return repository.findAll();
    }
}
