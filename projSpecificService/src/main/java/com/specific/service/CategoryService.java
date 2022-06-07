package com.specific.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.specific.model.Category;
import com.specific.repository.CategoryRepository;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository repository;

    public Category saveCategory(Category category) {
        return repository.save(category);
    }

    public List<Category> saveCategories(List<Category> categories) {
        return repository.saveAll(categories);
    }

    public List<Category> getCategories() {
        return repository.findAll();
    }

    public Category getCategoryById(long id) {
        return repository.findById(id);
    }

    public String deleteCategory(long id) {
        repository.deleteById(id);
        return "category removed !! " + id;
    }

    public Category updateCategory(Category category) {
        Category existingCategory = repository.findById(category.getId());
        existingCategory.setCategory(category.getCategory());
        existingCategory.setProducts(category.getProducts());
        return repository.save(existingCategory);
    }

    public Category findCategoryByName(String category) {
        return null;
    }
}
