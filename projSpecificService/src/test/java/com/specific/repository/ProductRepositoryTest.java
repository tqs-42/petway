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
import com.specific.model.Product;
import com.specific.model.Store;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {

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
    private ProductRepository productRepository;

    @Autowired
    private TestEntityManager entityManager;

    // Valid

    @Test
    void testCreateProductAndFindById_thenReturn() {

        Product product = new Product();
        product.setName("Dog Food");
        Category category = new Category();
        category.setName("Categoria");
        product.setCategory(category);
        product.setImage("/path");
        product.setPrice(15.0);
        Store store = new Store();
        store.setName("Loja");
        store.setAddress("A minha morada");
        product.setStore(store);

        entityManager.persistAndFlush(category);
        entityManager.persistAndFlush(store);
        entityManager.persistAndFlush(product);

        Product res = productRepository.findByName(product.getName());

        assertThat(res).isEqualTo(product);

    }

    @Test
    void testCreateProductsAndFindByAll_thenReturnThem() {

        Product p1 = new Product();
        p1.setName("Dog Food");
        Category category = new Category();
        category.setName("Categoria");
        p1.setCategory(category);
        p1.setImage("/path");
        p1.setPrice(15.0);
        Store store = new Store();
        store.setName("Loja");
        store.setAddress("A minha morada");
        p1.setStore(store);

        Product p2 = new Product();
        p2.setName("Dog Food");
        Category category2 = new Category();
        category2.setName("Categoria");
        p2.setCategory(category2);
        p2.setImage("/path");
        p2.setPrice(15.0);
        Store store2 = new Store();
        store2.setName("Loja");
        store2.setAddress("A minha morada");
        p2.setStore(store2);

        entityManager.persistAndFlush(category);
        entityManager.persistAndFlush(store);
        entityManager.persistAndFlush(category2);
        entityManager.persistAndFlush(store2);
        entityManager.persistAndFlush(p1);
        entityManager.persistAndFlush(p2);

        List<Product> allProducts = productRepository.findAll();

        assertThat(allProducts).isNotNull();
        assertThat(allProducts).hasSize(2).extracting(Product::getId).contains(p1.getId(),
                p2.getId());
        assertThat(allProducts).hasSize(2).extracting(Product::getName).contains(p1.getName(),
                p2.getName());
        assertThat(allProducts).hasSize(2).extracting(Product::getCategory).contains(p1.getCategory(),
                p2.getCategory());
        assertThat(allProducts).hasSize(2).extracting(Product::getStore).contains(p1.getStore(),
                p2.getStore());

    }

    // Invalid

    @Test
    void testWhenFindByInvalidName_thenReturnNull() {
        Product res = productRepository.findByName("Dont exist");
        assertThat(res).isNull();
    }

    @Test
    void testNoProducts_thenReturnEmpty() {
        List<Product> allProducts = productRepository.findAll();
        assertThat(allProducts).isNotNull().isEmpty();
    }

}