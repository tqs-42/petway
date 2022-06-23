package com.specific.integration;

import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.http.*;
import com.github.dockerjava.api.exception.ConflictException;
import com.specific.controller.ProductController;
import com.specific.model.Category;
import com.specific.model.Product;
import com.specific.model.Store;
import com.specific.repository.CategoryRepository;
import com.specific.repository.ProductRepository;
import com.specific.repository.StoreRepository;
import com.specific.service.ProductService;

import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.shaded.com.fasterxml.jackson.core.type.TypeReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductTemplateIT {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Container
    public static MySQLContainer<?> container = new MySQLContainer<>("mysql:8.0").withUsername("admin")
            .withPassword("admin");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
    }

    private String product;
    private Store store;
    private Category category;

    @BeforeEach
    void setUp() {
        this.category = new Category();
        this.store = new Store();

        this.category.setName("Categoria");
        this.store.setName("Loja");
        this.store.setAddress("A minha morada");

        categoryRepository.saveAndFlush(this.category);
        storeRepository.saveAndFlush(this.store);

        this.product = "{\"name\": \"PetyCity\", \"description\": \"Rua P\", \"image\": \"vera@ua.pt\", \"price\": 2.4, \"stock\": 5, \"store\":1, \"category\":1}";
    }

    @Test
    void testAddProduct_thenCode200() {

        RestAssured.given().contentType("application/json").body(this.product).when().post(getBaseUrl() + "/add")
                .then()
                .statusCode(200)
                .and().body("name", equalTo("PetyCity"))
                .and().body("description", equalTo("Rua P"))
                .and().body("image", equalTo("vera@ua.pt"))
                .and().body("price", equalTo(2.4F))
                .and().body("stock", equalTo(5));

    }

    @Test
    void testProductAlreadyExists_thenReturnConflit() throws ConflictException

    {
        String sameNameProduct = "{\"name\": \"PetyCity\", \"description\": \"Outra descricao\", \"image\": \"vera@ua.pt\", \"price\": 2.4, \"stock\": 5, \"store\":1, \"category\":1}";

        RestAssured.given()
                .contentType("application/json")
                .body(sameNameProduct)
                .when()
                .post(getBaseUrl() + "/add")
                .then()
                .statusCode(409);

    }

    @Test
    public void testWhenGetById_thenOKRequest() {
        String sameNameProduct = "{\"name\": \"AnotherCity\", \"description\": \"Outra descricao\", \"image\": \"imagem\", \"price\": 2.5, \"stock\": 55, \"store\":1, \"category\":1}";

        RestAssured.given()
                .contentType("application/json")
                .body(sameNameProduct)
                .when()
                .post(getBaseUrl() + "/add")
                .then()
                .statusCode(200);

        RestAssured.given()
                .contentType("application/json")
                .body(sameNameProduct)
                .when()
                .get(getBaseUrl() + "/1")
                .then()
                .statusCode(200)
                .and().body("name", equalTo("AnotherCity"))
                .and().body("description", equalTo("Outra descricao"))
                .and().body("image", equalTo("imagem"))
                .and().body("price", equalTo(2.5F))
                .and().body("stock", equalTo(55));

    }

    String getBaseUrl() {
        return "http://localhost:" + randomServerPort + "/products";
    }

}