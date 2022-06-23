package com.specific.controller;

import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.annotation.FilterType;

import com.specific.config.JwtRequestFilter;
import com.specific.config.WebSecurityConfig;
import com.specific.model.Cart;
import com.specific.model.Client;
import com.specific.model.Request;
import com.specific.model.RequestEvents;
import com.specific.model.RequestStatus;
import com.specific.service.RequestEventsService;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

@WebMvcTest(value = RequestEventsController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebSecurityConfig.class) })
@AutoConfigureMockMvc(addFilters = false)
class RequestEventControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RequestEventsService requestEventsService;

    @MockBean
    private JwtRequestFilter jwtRequestFilter;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mvc);
    }

    @Test
    void whenGetRequestEvents_thenReturnRequestEventsByUser() throws Exception {

        // Request request = new Request(1, "Rua de Cima em Baixo, n34, Aveiro", new
        // Cart(new Client("diana@ua.pt")));

        // List<RequestEvents> ret = new ArrayList<>();
        // RequestEvents requestEvents1 = new RequestEvents(1, RequestStatus.DELIVERED,
        // new Date(2022, 6, 12));
        // ret.add(requestEvents1);

        // when(requestEventsService.getAllRequestEvents("diana@ua.pt")).thenReturn((Set<RequestEvents>)
        // ret);

        // RestAssuredMockMvc.given().contentType(ContentType.JSON)
        // .when().get("/requestEvents/diana@ua.pt").then().assertThat().statusCode(200).and()
        // .body("", equalTo(""));
    }

}
