package com.engine.app.controller;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.concurrent.ExecutionException;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.web.servlet.MockMvc;

import com.engine.app.config.JwtRequestFilter;
import com.engine.app.config.JwtTokenUtil;
import com.engine.app.config.WebSecurityConfig;
import com.engine.app.exception.ConflictException;
import com.engine.app.exception.InvalidCredentialsException;
import com.engine.app.exception.PersonNotFoundException;
import com.engine.app.model.JwtRequest;
import com.engine.app.model.JwtResponse;
import com.engine.app.model.Rider;
import com.engine.app.service.JwtUserDetailsService;
import com.engine.app.service.RiderService;


@WebMvcTest(value = JwtAuthenticationController.class, excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebSecurityConfig.class)})
@AutoConfigureMockMvc(addFilters = false)
class JwtAuthenticationControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RiderService riderService;

    @MockBean
    private JwtRequestFilter jwtRequest;

    @MockBean
    private JwtUserDetailsService userDetailsService;

    @Test
    void testRegisterRiderWhenValid_thenStatus200() throws Exception {

        JSONObject payload = new JSONObject();
        payload.put("email", "rider123@ua.pt");
        payload.put("address", "Rua da estrada");
        payload.put("fullname", "John Smith");
        payload.put("password", "password");

        Rider rider = new Rider("rider@ua.pt", "Rua da estrada", "John Smith", "password");

        when(riderService.registerRider(anyString(),anyString(),anyString(),anyString())).thenReturn(rider);

        mvc.perform(post("/register")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer token")
            .characterEncoding("UTF-8")
            .content(String.valueOf(payload))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.email", is(rider.getEmail())))
            .andExpect(jsonPath("$.address", is(rider.getAddress())))
            .andExpect(jsonPath("$.fullname", is(rider.getFullname())));

        verify(riderService, times(1)).registerRider(anyString(),anyString(),anyString(),anyString());

    }

    @Test
    void testRegisterRiderWhenUserAlreadyExists_thenStatus400() throws Exception {

        JSONObject payload = new JSONObject();
        payload.put("email", "rider123@ua.pt");
        payload.put("address", "Rua da estrada");
        payload.put("fullname", "John Smith");
        payload.put("password", "password");

        when(riderService.registerRider(anyString(),anyString(),anyString(),anyString())).thenThrow(ConflictException.class);

        mvc.perform(post("/register")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer token")
            .characterEncoding("UTF-8")
            .content(String.valueOf(payload))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());

        verify(riderService, times(1)).registerRider(anyString(),anyString(),anyString(),anyString());

    }

    @Test
    void testLoginValidRider_thenStatus200() throws Exception {

        JwtResponse expectedResponse = new JwtResponse("token123", new SimpleGrantedAuthority("Rider"), "rider123@ua.pt");

        JSONObject payload = new JSONObject();
        payload.put("email", "rider123@ua.pt");
        payload.put("password", "password");

        when(userDetailsService.generateTokenLogin(any(JwtRequest.class))).thenReturn(expectedResponse);

        mvc.perform(post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .content(String.valueOf(payload))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("token", is(expectedResponse.getToken())))
            .andExpect(jsonPath("['role']['authority']", is(expectedResponse.getRole().getAuthority())))
            .andExpect(jsonPath("email", is(expectedResponse.getEmail())));

        verify(userDetailsService, times(1)).generateTokenLogin(any(JwtRequest.class));

    }

    @Test
    void testLoginValidAdmin_thenStatus200() throws Exception {

        JwtResponse expectedResponse = new JwtResponse("token123", new SimpleGrantedAuthority("Admin"), "admin123@ua.pt");

        JSONObject payload = new JSONObject();
        payload.put("email", "admin123@ua.pt");
        payload.put("password", "password");

        when(userDetailsService.generateTokenLogin(any(JwtRequest.class))).thenReturn(expectedResponse);

        mvc.perform(post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .content(String.valueOf(payload))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("token", is(expectedResponse.getToken())))
            .andExpect(jsonPath("['role']['authority']", is(expectedResponse.getRole().getAuthority())))
            .andExpect(jsonPath("email", is(expectedResponse.getEmail())));

        verify(userDetailsService, times(1)).generateTokenLogin(any(JwtRequest.class));

    }

    @Test
    void testLoginInvalidCredentials_thenStatus401() throws Exception {

        JSONObject payload = new JSONObject();
        payload.put("email", "rider123@ua.pt");
        payload.put("password", "password");

        when(userDetailsService.generateTokenLogin(any(JwtRequest.class))).thenThrow(InvalidCredentialsException.class);

        mvc.perform(post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .content(String.valueOf(payload))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isUnauthorized());

        verify(userDetailsService, times(1)).generateTokenLogin(any(JwtRequest.class));

    }

}