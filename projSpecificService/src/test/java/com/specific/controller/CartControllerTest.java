package com.specific.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MockMvc;

import com.specific.config.JwtRequestFilter;
import com.specific.config.WebSecurityConfig;
import com.specific.exception.ConflictException;
import com.specific.exception.ResourceNotFoundException;
import com.specific.model.Client;
import com.specific.model.Product;
import com.specific.model.RequestProducts;
import com.specific.service.CartService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import static org.hamcrest.Matchers.*;

@WebMvcTest(value = CartController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebSecurityConfig.class) })
@AutoConfigureMockMvc(addFilters = false)
class CartControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private CartService service;

    @MockBean
    private JwtRequestFilter jwtRequestFilter;

    private Client client;
    private Product product;
    private RequestProducts requestProducts;

    @BeforeEach
    public void setUp() throws Exception {
        RestAssuredMockMvc.mockMvc(mvc);

        client = new Client("eva@ua.pt", "qwertyui", "Eva Bartolomeu", "Aveiro");
        client.getCart().setId(1L);

        product = new Product();
        product.setId(1L);

        requestProducts = new RequestProducts(3, client.getCart(), product);
        requestProducts.setId(1L);
    }

    // verificar mais campos
    @Test
    void testWhenGetRequestProductsByValidEmailAndValidproductId_thenReturnRequestProducts()
            throws ResourceNotFoundException {
        when(service.getProductAmout("eva@ua.pt", 1L)).thenReturn(Optional.of(requestProducts));

        RestAssuredMockMvc.given().contentType(ContentType.JSON)
                .when().get("/carts/user/eva@ua.pt/product/1").then().statusCode(200)
                .body("id", equalTo(1))
                .body("amount", equalTo(3))
                .body("cart.id", equalTo(1))
                .body("product.id", equalTo(1));

        verify(service, times(1)).getProductAmout("eva@ua.pt", 1L);
    }

    @Test
    void testWhenGetRequestProductsByInvalidEmailAndValidproductId_thenReturnStatus404()
            throws ResourceNotFoundException {
        when(service.getProductAmout("eva@ua.pt", 1L)).thenReturn(Optional.of(requestProducts));

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/carts/user/b/product/1")
                .then()
                .statusCode(404);

        verify(service, times(1)).getProductAmout("b", 1L);
    }

    @Test
    void testWhenGetRequestProductsByValidEmailAndInvalidproductId_thenReturnStatus404()
            throws ResourceNotFoundException {
        when(service.getProductAmout("eva@ua.pt", 1L)).thenReturn(Optional.of(requestProducts));

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/carts/user/eva@ua.pt/product/2")
                .then()
                .statusCode(404);

        verify(service, times(1)).getProductAmout("eva@ua.pt", 2L);
    }

    @Test
    void testWhenGetRequestProductsByInvalidEmailAndInvalidproductId_thenReturnStatus404()
            throws ResourceNotFoundException {
        when(service.getProductAmout("eva@ua.pt", 1L)).thenReturn(Optional.of(requestProducts));

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/carts/user/b/product/2")
                .then()
                .statusCode(404);

        verify(service, times(1)).getProductAmout("b", 2L);
    }

    // verificar mais campos
    @Test
    void testWhenGetRequestsProductsByValidEmail_thenReturnJsonArray() throws ResourceNotFoundException {
        RequestProducts requestProducts2 = new RequestProducts(4, client.getCart(), new Product());
        requestProducts2.setId(2L);
        RequestProducts requestProducts3 = new RequestProducts(5, client.getCart(), new Product());
        requestProducts3.setId(3L);

        List<RequestProducts> allRequestProducts = Arrays.asList(requestProducts, requestProducts2, requestProducts3);

        when(service.getProducts("eva@ua.pt")).thenReturn(Optional.of(allRequestProducts));

        RestAssuredMockMvc.given().contentType(ContentType.JSON)
                .when().get("/carts/user/eva@ua.pt/products").then().statusCode(200)
                .body("$.size()", equalTo(3))
                .body("[0].id", equalTo(1))
                .body("[1].id", equalTo(2))
                .body("[2].id", equalTo(3))
                .body("[0].amount", equalTo(3))
                .body("[1].amount", equalTo(4))
                .body("[2].amount", equalTo(5))
                .body("[0].cart.id", equalTo(1))
                .body("[1].cart.id", equalTo(1))
                .body("[2].cart.id", equalTo(1))
                .body("[0].product.id", equalTo(1))
                .body("[1].product.id", equalTo((int) requestProducts2.getProduct().getId()))
                .body("[2].product.id", equalTo((int) requestProducts3.getProduct().getId()));

        verify(service, times(1)).getProducts("eva@ua.pt");
    }

    @Test
    void testWhenGetRequestsProductsByInvalidEmail_thenReturnStatus404() throws ResourceNotFoundException {
        RestAssuredMockMvc.given().contentType(ContentType.JSON)
                .when().get("/carts/user/eva@ua.pt/products").then().statusCode(404);

        verify(service, times(1)).getProducts("eva@ua.pt");
    }

    @Test
    void testWhenPutRequestProductsByValidEmailValidproductId_thenReturnRequestProducts()
            throws ResourceNotFoundException, ConflictException {
        when(service.putProductAmout("eva@ua.pt", 1L, 3)).thenReturn(Optional.of(requestProducts));

        RestAssuredMockMvc.given().contentType(ContentType.JSON)
                .when().put("carts/user/eva@ua.pt/product/1/amount/3").then().statusCode(200)
                .body("id", equalTo((int) requestProducts.getId()))
                .body("amount", equalTo(3))
                .body("cart.id", equalTo(1))
                .body("product.id", equalTo(1));

        verify(service, times(1)).putProductAmout("eva@ua.pt", 1L, 3);
    }

    // FALTA TESTAR QUANDO DA O ConflictException
    @Test
    void testWhenPutRequestProductsByInvalidEmailValidproductId_thenReturnStatus404()
            throws ResourceNotFoundException, ConflictException {
        System.out.println("----------------AQUI---------------------------");
        when(service.putProductAmout("eva@ua.pt", 1L, 3)).thenReturn(Optional.of(requestProducts));

        RestAssuredMockMvc.given().contentType(ContentType.JSON)
                .when().put("carts/user/b/product/1/amount/3").then().statusCode(404);

        verify(service, times(1)).putProductAmout("b", 1L, 3);
    }

    @Test
    void testWhenPutRequestProductsByValidEmailInvalidproductId_thenReturnStatus404()
            throws ResourceNotFoundException, ConflictException {
        when(service.putProductAmout("eva@ua.pt", 1L, 3)).thenReturn(Optional.of(requestProducts));

        RestAssuredMockMvc.given().contentType(ContentType.JSON)
                .when().put("carts/user/eva@ua.pt/product/20/amount/3").then().statusCode(404);

        verify(service, times(1)).putProductAmout("eva@ua.pt", 20L, 3);
    }

    @Test
    void testWhenPutRequestProductsByInvalidEmailInvalidproductId_thenReturnStatus404()
            throws ResourceNotFoundException, ConflictException {
        when(service.putProductAmout("eva@ua.pt", 1L, 3)).thenReturn(Optional.of(requestProducts));

        RestAssuredMockMvc.given().contentType(ContentType.JSON)
                .when().put("carts/user/b/product/20/amount/3").then().statusCode(404);

        verify(service, times(1)).putProductAmout("b", 20L, 3);
    }
}