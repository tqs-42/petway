package com.specific.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.specific.exception.ConflictException;
import com.specific.model.Category;
import com.specific.service.CategoryService;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.restassured.http.ContentType;

import static org.hamcrest.Matchers.*;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mvc);
    }

    // Valid Tests

    @Test
    void whenGetCategories_thenReturnAllCategories() throws Exception {
        List<Category> categories = new ArrayList<>();
        Category category1 = new Category("Dog Food");
        Category category2 = new Category("Toys");
        Category category3 = new Category("Parrot Food");
        categories.add(category1);
        categories.add(category2);
        categories.add(category3);

        when(categoryService.getCategories()).thenReturn(categories);

        RestAssuredMockMvc.given().contentType(ContentType.JSON)
                .when().get("/categories").then().assertThat().statusCode(200).and()
                .body("[0].name", equalTo("Dog Food"))
                .body("[1].name", equalTo("Toys"))
                .body("[2].name", equalTo("Parrot Food"));

        verify(categoryService, times(1)).getCategories();

    }

    @Test
    void whenCreateCategory_thenCreateCategory() throws Exception {
        Category category = new Category("Toys");

        when(categoryService.saveCategory(Mockito.any())).thenReturn(category);
        RestAssuredMockMvc.given().contentType(ContentType.JSON).body(category)
                .when().post("/categories/add").then().assertThat().statusCode(200)
                .body("name", equalTo("Toys"));
        verify(categoryService, times(1)).saveCategory(Mockito.any());

    }

    // Invalid Tests

    @Test
    void whenPostExistingCategory_thenShouldNotCreate() throws Exception {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category("Dog Food"));
        categories.add(new Category("Toys"));
        categories.add(new Category("Treats"));
        when(categoryService.getCategories()).thenReturn(categories);

        Category category = new Category("Toys");

        doThrow(new ConflictException("Category already exists")).when(categoryService).saveCategory(Mockito.any());

        RestAssuredMockMvc.given().contentType(ContentType.JSON).body(category)
                .when().post("/categories/add").then().assertThat().statusCode(409);

        verify(categoryService, times(1)).saveCategory(Mockito.any());

    }

}
