package com.specific.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.specific.config.JwtRequestFilter;
import com.specific.config.WebSecurityConfig;
import com.specific.controller.ManagerController;
import com.specific.exception.ConflictException;
import com.specific.exception.ResourceNotFoundException;
import com.specific.model.Category;
import com.specific.model.Manager;
import com.specific.model.Store;
import com.specific.repository.ManagerRepository;
import com.specific.service.CategoryService;
import com.specific.service.ManagerService;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.context.annotation.FilterType;
import com.specific.config.JwtRequestFilter;

//import io.restassured.module.mockmvc.RestAssuredMockMvc;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.restassured.http.ContentType;

@WebMvcTest(value = ManagerController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebSecurityConfig.class) })
@AutoConfigureMockMvc(addFilters = false)
class ManagerControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ManagerService managerService;

    @MockBean
    private ManagerRepository managerRepository;

    @MockBean
    private JwtRequestFilter jwtRequestFilter;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mvc);

    }

    // Invalid Tests

    @Test
    void whenGetInvalidEmail_then400() throws Exception {

        doThrow(new ResourceNotFoundException("Not found resource")).when(managerService)
                .getStore(Mockito.any());

        RestAssuredMockMvc.given().contentType(ContentType.JSON)
                .when().get("/managers/user/invalid/store").then().assertThat().statusCode(404);

        verify(managerService, times(1)).getStore(Mockito.any());

    }

}
