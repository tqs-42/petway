package com.engine.app.controller;


import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

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
import org.springframework.test.web.servlet.MockMvc;

import com.engine.app.config.JwtRequestFilter;
import com.engine.app.config.JwtTokenUtil;
import com.engine.app.exception.ConflictException;
import com.engine.app.model.Rider;
import com.engine.app.service.JwtUserDetailsService;
import com.engine.app.service.RiderService;


@WebMvcTest(value = RiderController.class, excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = JwtTokenUtil.class)})
@AutoConfigureMockMvc(addFilters = false)
class RiderControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RiderService riderService;

    @MockBean
    private JwtRequestFilter jwtRequest;

    @MockBean
    private JwtTokenUtil jwtTokenUtil;

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

        mvc.perform(post("/riders/register")
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

        mvc.perform(post("/riders/register")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer token")
            .characterEncoding("UTF-8")
            .content(String.valueOf(payload))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());

        verify(riderService, times(1)).registerRider(anyString(),anyString(),anyString(),anyString());

    }

    @Test
    void testGetRiderByEmail_thenStatus200() throws Exception {

        Rider rider = new Rider("rider@ua.pt", "Rua da estrada", "John Smith", "password");

        when(riderService.getRiderByEmail(anyString())).thenReturn(rider);

        mvc.perform(get("/riders/rider")
            .param("email", "rider@ua.pt")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer token")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.email", is(rider.getEmail())))
            .andExpect(jsonPath("$.address", is(rider.getAddress())))
            .andExpect(jsonPath("$.fullname", is(rider.getFullname())));

        verify(riderService, times(1)).getRiderByEmail(anyString());

    }

    @Test
    void testGetRiderByEmail_thenStatus400() throws Exception {

        when(riderService.getRiderByEmail(anyString())).thenReturn(null);

        mvc.perform(get("/riders/rider")
            .param("email", "otheremail@abc.xyz")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer token")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());

        verify(riderService, times(1)).getRiderByEmail(anyString());

    }

    @Test
    void testGetAllRiders_thenStatus200() throws Exception {

        ArrayList<Rider> allRiders = new ArrayList<>();
        allRiders.add(new Rider("rider@ua.pt", "Rua da estrada", "John Smith", "password"));
        allRiders.add(new Rider("cristiano@ua.pt", "Rua do prego", "Cristiano Ronaldo", "pass123"));
        allRiders.add(new Rider("aa@ua.pt", "Avenida Lourenço Peixinho", "António Armando", "password"));

        when(riderService.getAllRiders()).thenReturn(allRiders);

        mvc.perform(get("/riders/allRiders")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer token")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].email", is(allRiders.get(0).getEmail())))
            .andExpect(jsonPath("$[0].address", is(allRiders.get(0).getAddress())))
            .andExpect(jsonPath("$[0].fullname", is(allRiders.get(0).getFullname())))
            .andExpect(jsonPath("$[1].email", is(allRiders.get(1).getEmail())))
            .andExpect(jsonPath("$[1].address", is(allRiders.get(1).getAddress())))
            .andExpect(jsonPath("$[1].fullname", is(allRiders.get(1).getFullname())))
            .andExpect(jsonPath("$[2].email", is(allRiders.get(2).getEmail())))
            .andExpect(jsonPath("$[2].address", is(allRiders.get(2).getAddress())))
            .andExpect(jsonPath("$[2].fullname", is(allRiders.get(2).getFullname())));

        verify(riderService, times(1)).getAllRiders();

    }

    @Test
    void testGetAllActiveRiders_thenStatus200() throws Exception {

        ArrayList<Rider> activeRiders = new ArrayList<>();
        activeRiders.add(new Rider("rider@ua.pt", "Rua da estrada", "John Smith", "password", true));
        activeRiders.add(new Rider("cristiano@ua.pt", "Rua do prego", "Cristiano Ronaldo", "pass123", true));
        activeRiders.add(new Rider("aa@ua.pt", "Avenida Lourenço Peixinho", "António Armando", "password", true));

        when(riderService.getAllActiveRiders()).thenReturn(activeRiders);

        mvc.perform(get("/riders/activeRiders")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer token")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].email", is(activeRiders.get(0).getEmail())))
            .andExpect(jsonPath("$[0].address", is(activeRiders.get(0).getAddress())))
            .andExpect(jsonPath("$[0].fullname", is(activeRiders.get(0).getFullname())))
            .andExpect(jsonPath("$[0].isActive", is(activeRiders.get(0).getIsActive())))
            .andExpect(jsonPath("$[1].email", is(activeRiders.get(1).getEmail())))
            .andExpect(jsonPath("$[1].address", is(activeRiders.get(1).getAddress())))
            .andExpect(jsonPath("$[1].fullname", is(activeRiders.get(1).getFullname())))
            .andExpect(jsonPath("$[1].isActive", is(activeRiders.get(1).getIsActive())))
            .andExpect(jsonPath("$[2].email", is(activeRiders.get(2).getEmail())))
            .andExpect(jsonPath("$[2].address", is(activeRiders.get(2).getAddress())))
            .andExpect(jsonPath("$[2].fullname", is(activeRiders.get(2).getFullname())))
            .andExpect(jsonPath("$[2].isActive", is(activeRiders.get(2).getIsActive())));

        verify(riderService, times(1)).getAllActiveRiders();

    }

    @Test
    void testGetTotalRiders_thenStatus200() throws Exception {

        ArrayList<Rider> allRiders = new ArrayList<>();
        allRiders.add(new Rider("rider@ua.pt", "Rua da estrada", "John Smith", "password"));
        allRiders.add(new Rider("cristiano@ua.pt", "Rua do prego", "Cristiano Ronaldo", "pass123"));
        allRiders.add(new Rider("aa@ua.pt", "Avenida Lourenço Peixinho", "António Armando", "password"));

        when(riderService.getAllRiders()).thenReturn(allRiders);

        mvc.perform(get("/riders/totalRiders")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer token")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", is(allRiders.size())));

        verify(riderService, times(1)).getAllRiders();

    }

    @Test
    void testGetTotalActiveRiders_thenStatus200() throws Exception {

        ArrayList<Rider> activeRiders = new ArrayList<>();
        activeRiders.add(new Rider("rider@ua.pt", "Rua da estrada", "John Smith", "password"));
        activeRiders.add(new Rider("cristiano@ua.pt", "Rua do prego", "Cristiano Ronaldo", "pass123"));
        activeRiders.add(new Rider("aa@ua.pt", "Avenida Lourenço Peixinho", "António Armando", "password"));

        when(riderService.getAllActiveRiders()).thenReturn(activeRiders);

        mvc.perform(get("/riders/totalActiveRiders")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer token")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", is(activeRiders.size())));

        verify(riderService, times(1)).getAllActiveRiders();

    }

    @Test
    void testChangeStatusWhenValidRider_thenStatus200() throws Exception {

        Rider rider = new Rider("rider@ua.pt", "Rua da estrada", "John Smith", "password", true);

        when(riderService.changeStatus(anyString())).thenReturn(rider);

        mvc.perform(put("/riders/changeStatus/"+rider.getEmail())
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer token")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        verify(riderService, times(1)).changeStatus(anyString());

    }

    @Test
    void testChangeStatusWhenRiderDoesNotExist_thenStatus400() throws Exception {

        when(riderService.changeStatus(anyString())).thenThrow(BadCredentialsException.class);

        mvc.perform(put("/riders/changeStatus/invaliduser@gmail.com")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer token")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());

        verify(riderService, times(1)).changeStatus(anyString());

    }
    
}