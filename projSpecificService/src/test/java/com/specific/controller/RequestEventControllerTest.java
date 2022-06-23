package com.specific.controller;

import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
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
import com.specific.model.Product;
import com.specific.model.Request;
import com.specific.model.RequestEvents;
import com.specific.model.RequestProducts;
import com.specific.model.RequestStatus;
import com.specific.service.RequestEventsService;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

import static org.hamcrest.Matchers.*;

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
    void whenGetRequestEvent_thenReturnRequestEventsByID() throws Exception {

        Request request = new Request(1, "Rua de Cima em Baixo, n34, Aveiro", new Cart(new Client("diana@ua.pt")));

        RequestEvents requestEvents = new RequestEvents(request, RequestStatus.DELIVERED);

        when(requestEventsService.getRequestEvents(1)).thenReturn(requestEvents);

        RestAssuredMockMvc.given().contentType(ContentType.JSON)
                .when().get("/requestEvents/id/1").then().assertThat().statusCode(200);
    }

    @Test
    void whenGetRequestEvents_thenReturnRequestEventsByUser() throws Exception {

        Request request = new Request(1, "Rua de Cima em Baixo, n34, Aveiro", new Cart(new Client("diana@ua.pt")));

        Set<RequestEvents> ret = new HashSet<>();
        RequestEvents requestEvents1 = new RequestEvents(request, RequestStatus.DELIVERED);
        ret.add(requestEvents1);

        when(requestEventsService.getAllRequestEvents("diana@ua.pt")).thenReturn((Set<RequestEvents>) ret);

        RestAssuredMockMvc.given().contentType(ContentType.JSON)
                .when().get("/requestEvents/diana@ua.pt").then().assertThat().statusCode(200);
    }

}
