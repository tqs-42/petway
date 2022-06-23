package com.engine.app.controller;


import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.engine.app.config.JwtRequestFilter;
import com.engine.app.config.JwtTokenUtil;
import com.engine.app.exception.ConflictException;
import com.engine.app.exception.ResourceNotFoundException;
import com.engine.app.model.Delivery;
import com.engine.app.model.Review;
import com.engine.app.model.Rider;
import com.engine.app.model.Store;
import com.engine.app.service.JwtUserDetailsService;
import com.engine.app.service.StoreService;


@WebMvcTest(value = StoreController.class, excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = JwtTokenUtil.class)})
@AutoConfigureMockMvc(addFilters = false)
class StoreControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private StoreService storeService;

    @MockBean
    private JwtRequestFilter jwtRequest;

    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    @MockBean
    private JwtUserDetailsService userDetailsService;

    @Test
    void testCreateValidStore_thenStatus200() throws Exception {

<<<<<<< HEAD
        Store store = new Store(1L, "Petlandia", "Avenida Lourenço Peixinho n18");

        JSONObject payload = new JSONObject();
        payload.put("name", "Petlandia");
        payload.put("address","Avenida Lourenço Peixinho n18");
=======
        Store store = new Store(1L, "Petlandia", "Avenida Lourenço Peixinho n15");

        JSONObject payload = new JSONObject();
        payload.put("name", "Petlandia");
        payload.put("address", "Avenida Lourenço Peixinho n15");
>>>>>>> 320026fcf8651a1b872487133b560c32ab5d9790

        when(storeService.addStore(anyString(),anyString())).thenReturn(store);

        mvc.perform(post("/stores/store")
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .content(String.valueOf(payload))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(store.getId().intValue())))
            .andExpect(jsonPath("$.name", is(store.getName())))
            .andExpect(jsonPath("$.address", is(store.getAddress())));

        verify(storeService, times(1)).addStore(anyString(),anyString());

    }

    @Test
    void testCreateExistingStore_thenStatus400() throws Exception {

        JSONObject payload = new JSONObject();
        payload.put("name", "Petlandia");
<<<<<<< HEAD
        payload.put("address","Avenida Lourenço Peixinho n18");
=======
        payload.put("address", "Avenida Lourenço Peixinho n15");
>>>>>>> 320026fcf8651a1b872487133b560c32ab5d9790

        when(storeService.addStore(anyString(),anyString())).thenThrow(ConflictException.class);

        mvc.perform(post("/stores/store")
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .content(String.valueOf(payload))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());

        verify(storeService, times(1)).addStore(anyString(),anyString());

    }

    @Test
    void testDeleteStoreSuccess_thenStatus204() throws Exception {

        when(storeService.deleteStore(anyLong())).thenReturn(true);

        mvc.perform(delete("/stores/store")
            .param("id", "1")
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        verify(storeService, times(1)).deleteStore(anyLong());

    }

    @Test
    void testDeleteStoreNotFound_thenStatus400() throws Exception {

       when(storeService.deleteStore(anyLong())).thenThrow(ResourceNotFoundException.class);

        mvc.perform(delete("/stores/store")
            .param("id", "1")
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());

        verify(storeService, times(1)).deleteStore(anyLong());

    }

    @Test
    void testGetValidStore_thenStatus200() throws Exception {

<<<<<<< HEAD
        Store store = new Store(1L, "Petlandia", "Avenida Lourenço Peixinho n18");
=======
        Store store = new Store(1L, "Petlandia", "Avenida Lourenço Peixinho n15");
>>>>>>> 320026fcf8651a1b872487133b560c32ab5d9790

        when(storeService.getStore(anyString())).thenReturn(store);

        mvc.perform(get("/stores/store")
            .param("id", "1")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.name", is(store.getName())))
            .andExpect(jsonPath("$.address", is(store.getAddress())));

        verify(storeService, times(1)).getStore(anyString());

    }

    @Test
    void testGetInvalidStore_thenStatus400() throws Exception {

        when(storeService.getStore(anyString())).thenThrow(ResourceNotFoundException.class);

        mvc.perform(get("/stores/store")
            .param("id", "1")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());

        verify(storeService, times(1)).getStore(anyString());

    }

    @Test
    void testGetAllStores_thenStatus200() throws Exception {

        ArrayList<Store> stores = new ArrayList<>();
<<<<<<< HEAD
        stores.add(new Store(1L, "Petlandia", "Avenida Lourenço Peixinho n18"));
        stores.add(new Store(2L, "Perritoworld", "Rua do Pescador n1"));
=======
        stores.add(new Store(1L, "Petlandia", "Avenida Lourenço Peixinho n15"));
        stores.add(new Store(2L, "Perritoworld", "Rua da Conceição"));
>>>>>>> 320026fcf8651a1b872487133b560c32ab5d9790

        when(storeService.getAllStores()).thenReturn(stores);

        mvc.perform(get("/stores/stores")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id", is(stores.get(0).getId().intValue())))
            .andExpect(jsonPath("$[0].name", is(stores.get(0).getName())))
            .andExpect(jsonPath("$[0].address", is(stores.get(0).getAddress())))
            .andExpect(jsonPath("$[1].id", is(stores.get(1).getId().intValue())))
            .andExpect(jsonPath("$[1].name", is(stores.get(1).getName())))
            .andExpect(jsonPath("$[1].address", is(stores.get(1).getAddress())))
            .andExpect(jsonPath("$.length()", is(stores.size())));

        verify(storeService, times(1)).getAllStores();

    }

    @Test
    void testGetAllStoresWhenEmpty_thenStatus200() throws Exception {

        mvc.perform(get("/stores/stores")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()", is(0)));

        verify(storeService, times(1)).getAllStores();

    }
    
}