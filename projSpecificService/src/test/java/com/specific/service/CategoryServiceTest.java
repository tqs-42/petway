package com.specific.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock(lenient = true)
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    public void setUp() throws Exception {

        Category category = new Category();
        category.setId(1L);
        category.setName("Toys");
        Set<Product> set = new HashSet<Product>();
        Product p1 = new Product("Ball", category);
        Product p2 = new Product("Kong", category);
        set.add(p1);
        set.add(p2);
        category.setProducts(set);

        Category anothercategory = new Category();
        anothercategory.setId(2L);
        anothercategory.setName("Food");
        Set<Product> set2 = new HashSet<Product>();
        Product p3 = new Product("Nugget", anothercategory);
        Product p4 = new Product("Chicken", anothercategory);
        set.add(p3);
        set.add(p4);
        anothercategory.setProducts(set2);

        List<Category> allCategories = Arrays.asList(category, anothercategory);

        // Mock calls

        Mockito.when(categoryRepository.findAll()).thenReturn(allCategories);
        Mockito.when(categoryRepository.findById(1L)).thenReturn(category);
        Mockito.when(categoryRepository.findById(2L)).thenReturn(anothercategory);
        Mockito.when(categoryRepository.findById(-500L)).thenReturn(null);

    }

    @Test
    void testGetAll_thenReturn2Categories() {

        Category category1 = new Category();
        category1.setId(1L);

        Category category2 = new Category();
        category2.setId(2L);

        List<Category> allCategories = categoryService.getCategories();

        Mockito.verify(categoryRepository, VerificationModeFactory.times(1)).findAll();

        assertThat(allCategories).hasSize(2).extracting(Category::getId).contains(category1.getId(), category2.getId());
    }

    @Test
    void whenCreateCategory_thenReturnIt() throws ConflictException {
        Category category = new Category();
        category.setName("Nova Categoria");

        Mockito.when(categoryRepository.save(category)).thenReturn(category);

        assertThat(categoryService.saveCategory(category)).isEqualTo(category);

        Mockito.verify(categoryRepository, VerificationModeFactory.times(1)).save(category);
    }
}