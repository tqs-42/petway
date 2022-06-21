package com.specific.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.specific.config.JwtRequestFilter;
import com.specific.config.WebSecurityConfig;
import com.specific.controller.CategoryController;
import com.specific.model.Category;
import com.specific.model.Product;
import com.specific.service.CategoryService;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.context.annotation.FilterType;
//import io.restassured.module.mockmvc.RestAssuredMockMvc;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.restassured.http.ContentType;

import static org.hamcrest.Matchers.*;

@WebMvcTest(value = CategoryController.class, excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebSecurityConfig.class)})
@AutoConfigureMockMvc(addFilters = false)
class CategoryControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private JwtRequestFilter jwtRequestFilter;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mvc);
    }

    @Test
    void whenGetCategories_thenReturnAllCategories() throws Exception {
        List<Category> ret = new ArrayList<>();
        Category category1 = new Category("Dog Food");
        Category category2 = new Category("Toys");
        Category category3 = new Category("Parrot Food");
        ret.add(category1);
        ret.add(category2);
        ret.add(category3);

        when(categoryService.getCategories()).thenReturn(ret);

        RestAssuredMockMvc.given().contentType(ContentType.JSON)
                .when().get("/categories").then().assertThat().statusCode(200).and()
                .body("[0].name", equalTo("Dog Food"))
                .body("[1].name", equalTo("Toys"))
                .body("[2].name", equalTo("Parrot Food"));

        verify(categoryService, times(1)).getCategories();

    }

    @Test
    void whenPostInvalidCategory_thenFailCreate() throws Exception {
        // Category res = new Category("legumes");
        // when(categoryService.saveCategory(Mockito.any())).thenReturn(res);
        // List<Category> ret = new ArrayList<>();
        // ret.add(new Category("Legumes"));
        // ret.add(new Category("Fruta"));
        // ret.add(new Category("Doces"));
        // when(categoryService.getCategories()).thenReturn(ret);
        // RestAssuredMockMvc.given().contentType(ContentType.JSON)
        // .when().post("/categories?name=legumes").then().assertThat().statusCode(400);
        // verify(categoryService, times(1)).getCategories();

    }

}
