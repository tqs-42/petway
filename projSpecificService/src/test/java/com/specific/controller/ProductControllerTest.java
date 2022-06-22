package com.specific.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.specific.exception.ConflictException;
import com.specific.model.Product;
import com.specific.service.ProductService;

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

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductService productService;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mvc);
    }

    // Valid Tests

    @Test
    void whenGetProducts_thenReturnAll() {
        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setName("Racao de Gato");
        product1.setPrice(15.0);
        product1.setStock(10);

        Product product2 = new Product();
        product2.setName("Bola de borracha");
        product2.setPrice(2.0);
        product2.setStock(20);

        Product product3 = new Product();
        product3.setName("Ipobrufeno");
        product3.setPrice(30.0);
        product3.setStock(50);

        products.add(product1);
        products.add(product2);
        products.add(product3);

        when(productService.getProducts()).thenReturn(products);

        RestAssuredMockMvc.given().contentType(ContentType.JSON)
                .when().get("/products").then().assertThat().statusCode(200).and()
                .body("[0].name", equalTo("Racao de Gato")).body("[0].price", equalTo(15.0F))
                .body("[0].stock", equalTo(10))
                .body("[1].name", equalTo("Bola de borracha")).body("[1].price", equalTo(2.0F))
                .body("[1].stock", equalTo(20))
                .body("[2].name", equalTo("Ipobrufeno")).body("[2].price", equalTo(30.0F))
                .body("[2].stock", equalTo(50));

        verify(productService, times(1)).getProducts();

    }

    @Test
    void whenCreateProduct_thenCreateIt() throws Exception {
        Product product = new Product();
        product.setName("O meu Produto");

        when(productService.saveProduct(Mockito.any())).thenReturn(product);
        RestAssuredMockMvc.given().contentType(ContentType.JSON).body(product)
                .when().post("/products/add").then().assertThat().statusCode(200)
                .body("name", equalTo("O meu Produto"));
        verify(productService, times(1)).saveProduct(Mockito.any());

    }

    // Invalid Tests

    @Test
    void whenPostExistingProduct_thenShouldNotCreate() throws Exception {
        List<Product> products = new ArrayList<>();
        Product p1 = new Product();
        Product p2 = new Product();
        Product p3 = new Product();

        p1.setName("P1");
        p2.setName("P2");
        p2.setName("P3");

        products.add(p1);
        products.add(p2);
        products.add(p3);

        when(productService.getProducts()).thenReturn(products);

        Product produto = new Product();
        produto.setName("P1");

        doThrow(new ConflictException("Category already exists")).when(productService).saveProduct(Mockito.any());

        RestAssuredMockMvc.given().contentType(ContentType.JSON).body(produto)
                .when().post("/products/add").then().assertThat().statusCode(409);

        verify(productService, times(1)).saveProduct(Mockito.any());

    }

}
