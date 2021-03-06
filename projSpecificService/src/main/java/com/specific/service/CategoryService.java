package com.specific.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.specific.dto.CategoryDTO;
import com.specific.exception.ConflictException;
import com.specific.model.Category;
import com.specific.repository.CategoryRepository;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository repository;

    public Category saveCategory(CategoryDTO category) throws ConflictException {
        if (repository.findByName(category.getName()) != null) {
            throw new ConflictException("Category already exists");
        }
        return repository.save(new Category(category.getName()));
    }

    public List<Category> getCategories() {
        return repository.findAll();
    }
}
