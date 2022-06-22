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
import com.specific.model.Category;
import com.specific.repository.CategoryRepository;
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
class CategoryTemplateIT {

    @LocalServerPort
    int randomServerPort;

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

    private Category category1;
    private Category category2;

    @BeforeEach
    void setUp() {
        this.category1 = new Category("Dog Food");
        this.category2 = new Category("Toys");

        categoryRepository.saveAndFlush(category1);
        categoryRepository.saveAndFlush(category2);

    }

    @AfterEach
    void resetDb() {
        categoryRepository.deleteAll();
        categoryRepository.flush();
    }

    @Test
    void testCreateValidCategory_thenCreateIt() {
        Category newCategory = new Category("Food");

        RestAssured.given()
                .contentType("application/json")
                .body(newCategory)
                .when()
                .post(getBaseUrl() + "/add")
                .then()
                .body("name", equalTo(newCategory.getName()))
                .statusCode(200);
    }

    @Test
    void testCategoryAlreadyExists_thenReturnConflit() throws ConflictException

    {
        categoryRepository.saveAndFlush(new Category("Food"));

        Category sameCategory = new Category("Food");

        RestAssured.given()
                .contentType("application/json")
                .body(sameCategory)
                .when()
                .post(getBaseUrl() + "/add")
                .then()
                .statusCode(409);

    }

    @Test
    void testWhenAllCategory_thenOKRequest() {
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<List> response = testRestTemplate.exchange(
                getBaseUrl(), HttpMethod.GET, new HttpEntity<Object>(headers),
                List.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<Category> found = response.getBody();

        List<Category> categories = new ArrayList<>();

        for (Object category : found) {
            String categoria = String.valueOf(category);
            String id_str = categoria.split(",")[0].split("=")[1];
            long id = Long.parseLong(id_str);
            String name = categoria.split(",")[1].split("=")[1].replace("}", "");

            categories.add(new Category(id, name));
        }

        assertThat(categories.get(0).getName()).contains(category1.getName());
        assertThat(categories.get(1).getName()).contains(category2.getName());
    }

    String getBaseUrl() {
        return "http://localhost:" + randomServerPort + "/categories";
    }

}