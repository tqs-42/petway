package com.specific.repository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.specific.model.Category;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CategoryRepositoryTest {

    @Container
    public static MySQLContainer<?> container = new MySQLContainer<>("mysql:8.0").withUsername("admin")
            .withPassword("admin");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
    }

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TestEntityManager entityManager;

    // Valid

    @Test
    void testCreateCategoryAndFindById_thenReturn() {

        Category category = new Category();
        category.setName("Food");

        entityManager.persistAndFlush(category);

        Category res = categoryRepository.findById(category.getId());

        assertThat(res).isEqualTo(category);

    }

    @Test
    void testCreateCategoryAndFindByName_thenReturn() {

        Category category = new Category();
        category.setName("Food");

        entityManager.persistAndFlush(category);

        Category res = categoryRepository.findByName(category.getName());

        assertThat(res).isEqualTo(category);

    }

    @Test
    void testCreateCategoriesAndFindByAll_thenReturnThem() {

        Category c1 = new Category();
        c1.setName("Categoria 111");

        Category c2 = new Category();
        c2.setName("Categoria 255");

        entityManager.persistAndFlush(c1);
        entityManager.persistAndFlush(c2);

        List<Category> allCategories = categoryRepository.findAll();

        assertThat(allCategories).isNotNull();
        assertThat(allCategories).hasSize(2).extracting(Category::getId).contains(c1.getId(), c2.getId());
        assertThat(allCategories).hasSize(2).extracting(Category::getName).contains(c1.getName(), c2.getName());

    }

    // Invalid

    @Test
    void testWhenFindByInvalidId_thenReturnNull() {
        Category res = categoryRepository.findById(-1L);
        assertThat(res).isNull();
    }

    @Test
    void testWhenFindByInvalidName_thenReturnNull() {
        Category res = categoryRepository.findByName("Dont Exist");
        assertThat(res).isNull();
    }

    @Test
    void testNoCategories_thenReturnEmpty() {
        List<Category> allCategories = categoryRepository.findAll();
        assertThat(allCategories).isNotNull().isEmpty();
    }

}