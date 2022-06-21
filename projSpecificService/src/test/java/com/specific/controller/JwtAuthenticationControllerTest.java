package com.specific.controller;


import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

import com.specific.config.JwtRequestFilter;
import com.specific.config.WebSecurityConfig;
import com.specific.exception.ConflictException;
import com.specific.exception.InvalidCredentialsException;
import com.specific.model.Client;
import com.specific.model.JwtRequest;
import com.specific.model.JwtResponse;
import com.specific.model.Manager;
import com.specific.model.Store;
import com.specific.service.ClientService;
import com.specific.service.JwtUserDetailsService;
import com.specific.service.ManagerService;
import com.specific.service.StoreService;


@WebMvcTest(value = JwtAuthenticationController.class, excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebSecurityConfig.class)})
@AutoConfigureMockMvc(addFilters = false)
class JwtAuthenticationControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ClientService clientService;

    @MockBean
    private ManagerService managerService;

    @MockBean
    private JwtRequestFilter jwtRequest;

    @MockBean
    private StoreService storeService;

    @MockBean
    private JwtUserDetailsService userDetailsService;

    @Test
    void testRegisterClientWhenValid_thenStatus200() throws Exception {

        JSONObject payload = new JSONObject();
        payload.put("email", "client123@ua.pt");
        payload.put("address", "Rua da estrada");
        payload.put("fullname", "John Smith");
        payload.put("password", "password");

        Client client = new Client("rider@ua.pt", "Rua da estrada", "John Smith", "password");

        when(clientService.saveClient(any())).thenReturn(client);

        mvc.perform(post("/registerClient")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer token")
            .characterEncoding("UTF-8")
            .content(String.valueOf(payload))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.email", is(client.getEmail())))
            .andExpect(jsonPath("$.address", is(client.getAddress())))
            .andExpect(jsonPath("$.fullname", is(client.getFullname())));

        verify(clientService, times(1)).saveClient(any());

    }

    @Test
    void testRegisterClientWhenUserAlreadyExists_thenStatus400() throws Exception {

        JSONObject payload = new JSONObject();
        payload.put("email", "Client123@ua.pt");
        payload.put("address", "Rua da estrada");
        payload.put("fullname", "John Smith");
        payload.put("password", "password");

        when(clientService.saveClient(any())).thenThrow(ConflictException.class);

        mvc.perform(post("/registerClient")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer token")
            .characterEncoding("UTF-8")
            .content(String.valueOf(payload))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());

        verify(clientService, times(1)).saveClient(any());

    }

    @Test
    void testRegisterManagerWhenValid_thenStatus200() throws Exception {

        JSONObject payload = new JSONObject();
        payload.put("email", "Manager123@ua.pt");
        payload.put("fullname", "John Smith");
        payload.put("password", "password");
        payload.put("name", "Petlandia");
        payload.put("address", "Rua da estrada");

        Store store = new Store("Petlandia", "Rua da estrada", true);
        Manager manager = new Manager("Manager@ua.pt", "password", "John Smith", store);

        when(storeService.saveStore(any())).thenReturn(store);
        when(managerService.saveManager(any())).thenReturn(manager);

        mvc.perform(post("/registerManager")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer token")
            .characterEncoding("UTF-8")
            .content(String.valueOf(payload))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.email", is(manager.getEmail())))
            .andExpect(jsonPath("$.fullname", is(manager.getFullname())))
            .andExpect(jsonPath("$.store.name", is(manager.getStore().getName())))
            .andExpect(jsonPath("$.store.address", is(manager.getStore().getAddress())))
            .andExpect(jsonPath("$.store.active", is(manager.getStore().getActive())));

        verify(storeService, times(1)).saveStore(any());
        verify(managerService, times(1)).saveManager(any());

    }

    @Test
    void testRegisterManagerWhenUserAlreadyExists_thenStatus400() throws Exception {

        JSONObject payload = new JSONObject();
        payload.put("email", "Manager123@ua.pt");
        payload.put("fullname", "John Smith");
        payload.put("password", "password");
        payload.put("name", "Petlandia");
        payload.put("address", "Rua da estrada");

        Store store = new Store("Petlandia", "Rua da estrada", true);

        when(storeService.saveStore(any())).thenReturn(store);
        when(managerService.saveManager(any())).thenThrow(ConflictException.class);

        mvc.perform(post("/registerManager")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer token")
            .characterEncoding("UTF-8")
            .content(String.valueOf(payload))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());

        verify(storeService, times(1)).saveStore(any());
        verify(managerService, times(1)).saveManager(any());

    }

    @Test
    void testRegisterManagerWhenStoreAlreadyExists_thenStatus400() throws Exception {

        JSONObject payload = new JSONObject();
        payload.put("email", "Manager123@ua.pt");
        payload.put("fullname", "John Smith");
        payload.put("password", "password");
        payload.put("name", "Petlandia");
        payload.put("address", "Rua da estrada");

        when(storeService.saveStore(any())).thenThrow(ConflictException.class);

        mvc.perform(post("/registerManager")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer token")
            .characterEncoding("UTF-8")
            .content(String.valueOf(payload))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());

            verify(storeService, times(1)).saveStore(any());

    }

    @Test
    void testLoginValidClient_thenStatus200() throws Exception {

        JwtResponse expectedResponse = new JwtResponse("token123", new SimpleGrantedAuthority("Client"), "client123@ua.pt");

        JSONObject payload = new JSONObject();
        payload.put("email", "client123@ua.pt");
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
    void testLoginValidManager_thenStatus200() throws Exception {

        JwtResponse expectedResponse = new JwtResponse("token123", new SimpleGrantedAuthority("Manager"), "manager123@ua.pt");

        JSONObject payload = new JSONObject();
        payload.put("email", "manager123@ua.pt");
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