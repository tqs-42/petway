package com.specific.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.specific.dto.CategoryDTO;
import com.specific.exception.ConflictException;
import com.specific.model.Category;
import com.specific.model.Product;
import com.specific.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock(lenient = true)
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    private Category category;
    private CategoryDTO categorya;

    @BeforeEach
    public void setUp() throws Exception {

        category = new Category();
        category.setName("Toys");

        Category anothercategory = new Category();
        anothercategory.setId(2L);
        anothercategory.setName("Food");
        Set<Product> set2 = new HashSet<Product>();
        Product p3 = new Product("Nugget", anothercategory);
        Product p4 = new Product("Chicken", anothercategory);
        set2.add(p3);
        set2.add(p4);
        anothercategory.setProducts(set2);

        List<Category> allCategories = Arrays.asList(category, anothercategory);

        categorya = new CategoryDTO("Toys");

        // Mock calls

        Mockito.when(categoryRepository.findAll()).thenReturn(allCategories);
        Mockito.when(categoryRepository.findById(category.getId())).thenReturn(category);
        Mockito.when(categoryRepository.findById(2L)).thenReturn(anothercategory);
        Mockito.when(categoryRepository.findById(-500L)).thenReturn(null);

    }

    @Test
    void testGetAll_thenReturn2Categories() {

        Category category1 = new Category();

        Category category2 = new Category();
        category2.setId(2L);

        List<Category> allCategories = categoryService.getCategories();

        Mockito.verify(categoryRepository, VerificationModeFactory.times(1)).findAll();

        assertThat(allCategories).hasSize(2).extracting(Category::getId).contains(category1.getId(), category2.getId());
    }

    @Test
    void whenCreateCategory_thenReturnIt() throws ConflictException {
        Mockito.when(categoryRepository.save(category)).thenReturn(category);

        Category category2 = categoryService.saveCategory(categorya);

    }
}