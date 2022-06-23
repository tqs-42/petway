package com.engine.app.controller;


import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;
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
import com.engine.app.exception.InvalidReviewException;
import com.engine.app.exception.ResourceNotFoundException;
import com.engine.app.model.Delivery;
import com.engine.app.model.DeliveryStatus;
import com.engine.app.model.Event;
import com.engine.app.model.Review;
import com.engine.app.model.Rider;
import com.engine.app.model.Store;
import com.engine.app.service.DeliveryService;
import com.engine.app.service.EventService;
import com.engine.app.service.JwtUserDetailsService;
import com.engine.app.service.ReviewService;
import com.engine.app.service.RiderService;
import com.engine.app.service.StoreService;


@WebMvcTest(value = DeliveryController.class, excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = JwtTokenUtil.class)})
@AutoConfigureMockMvc(addFilters = false)
class DeliveryControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private DeliveryService deliveryService;

    @MockBean
    private StoreService storeService;

    @MockBean
    private EventService eventService;

    @MockBean
    private ReviewService reviewService;

    @MockBean
    private RiderService riderService;

    @MockBean
    private JwtRequestFilter jwtRequest;

    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    @MockBean
    private JwtUserDetailsService userDetailsService;

    @Test
    void testGetValidDelivery_thenStatus200() throws Exception {

<<<<<<< HEAD
        Delivery delivery = new Delivery(1L, null, null, new Store(1L, "Petlandia", "Avenida Lourenço Peixinho n18"));
=======
        Delivery delivery = new Delivery(1L, null, null, new Store(1L, "Petlandia", "Avenida Lourenço Peixinho"),"Rua da Pega n13");
>>>>>>> 320026fcf8651a1b872487133b560c32ab5d9790

        when(deliveryService.getDelivery(anyLong())).thenReturn(delivery);

        mvc.perform(get("/deliveries/delivery")
            .param("id", String.valueOf(delivery.getId()))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(delivery.getId().intValue())))
            .andExpect(jsonPath("$.rider", is(delivery.getRider())))
            .andExpect(jsonPath("$.review", is(delivery.getReview())))
            .andExpect(jsonPath("$.store.id", is(delivery.getStore().getId().intValue())))
            .andExpect(jsonPath("$.store.name", is(delivery.getStore().getName())))
            .andExpect(jsonPath("$.store.address", is(delivery.getStore().getAddress())));

        verify(deliveryService, times(1)).getDelivery(anyLong());

    }


    @Test
    void testGetInvalidDelivery_thenStatus400() throws Exception {

        when(deliveryService.getDelivery(anyLong())).thenReturn(null);

        mvc.perform(get("/deliveries/delivery")
            .param("id", "1")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());

        verify(deliveryService, times(1)).getDelivery(anyLong());

    }

    @Test
    void testCreateValidDelivery_thenStatus200() throws Exception {

<<<<<<< HEAD
        Store store = new Store(1L, "Petlandia", "Avenida Lourenço Peixinho n18");
        Delivery delivery = new Delivery(1L, null, null, store);
=======
        Store store = new Store(1L, "Petlandia", "Avenida Lourenço Peixinho");
        Delivery delivery = new Delivery(1L, null, null, store, "Rua da Pega n13");
>>>>>>> 320026fcf8651a1b872487133b560c32ab5d9790

        JSONObject payload = new JSONObject();
        payload.put("id", "1");

        when(storeService.getStore(anyString())).thenReturn(store);
        when(deliveryService.createDelivery(any(),anyString())).thenReturn(delivery);
        when(eventService.createEvent(any(), any())).thenReturn(new Event(delivery, DeliveryStatus.PENDING));

        mvc.perform(post("/deliveries/delivery")
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .content(String.valueOf(payload))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(delivery.getId().intValue())))
            .andExpect(jsonPath("$.rider", is(delivery.getRider())))
            .andExpect(jsonPath("$.review", is(delivery.getReview())))
            .andExpect(jsonPath("$.store.id", is(delivery.getStore().getId().intValue())))
            .andExpect(jsonPath("$.store.name", is(delivery.getStore().getName())))
            .andExpect(jsonPath("$.store.address", is(delivery.getStore().getAddress())));

        verify(storeService, times(1)).getStore(anyString());
        verify(deliveryService, times(1)).createDelivery(any(),anyString());
        verify(eventService, times(1)).createEvent(any(), any());

    }

    @Test
    void testCreateDeliveryWhenInvalidStore_thenStatus400() throws Exception {

        JSONObject payload = new JSONObject();
        payload.put("id", "1");

        when(storeService.getStore(anyString())).thenReturn(null);

        mvc.perform(post("/deliveries/delivery")
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .content(String.valueOf(payload))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());

        verify(storeService, times(1)).getStore(anyString());

    }

    @Test
    void testSetDeliveryValidRider_thenStatus200() throws Exception {

<<<<<<< HEAD
        Store store = new Store(1L, "Petlandia", "Avenida Lourenço Peixinho n18");
        Delivery delivery = new Delivery(1L, null, null, store);
=======
        Store store = new Store(1L, "Petlandia", "Avenida Lourenço Peixinho");
        Delivery delivery = new Delivery(1L, null, null, store, "Rua da Pega n13");
>>>>>>> 320026fcf8651a1b872487133b560c32ab5d9790
        Rider rider = new Rider("chicodatina@gmail.com", "Rua da Concertina", "Chico da Tina");

        JSONObject payload = new JSONObject();
        payload.put("delivery", "1");
        payload.put("rider", rider.getEmail());

        when(deliveryService.getDelivery(anyLong())).thenReturn(delivery);
        when(riderService.getRiderByEmail(anyString())).thenReturn(rider);

        mvc.perform(put("/deliveries/deliveryRider")
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .content(String.valueOf(payload))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        verify(deliveryService, times(1)).getDelivery(anyLong());
        verify(riderService, times(1)).getRiderByEmail(anyString());

    }

    @Test
    void testSetDeliveryRiderWhenInvalidDelivery_thenStatus400() throws Exception {

        JSONObject payload = new JSONObject();
        payload.put("delivery", "1");
        payload.put("rider", "randomemail@ua.pt");

        when(deliveryService.getDelivery(anyLong())).thenReturn(null);

        mvc.perform(put("/deliveries/deliveryRider")
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .content(String.valueOf(payload))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());

        verify(deliveryService, times(1)).getDelivery(anyLong());

    }

    @Test
    void testSetDeliveryInvalidRider_thenStatus400() throws Exception {

<<<<<<< HEAD
        Store store = new Store(1L, "Petlandia", "Avenida Lourenço Peixinho n18");
        Delivery delivery = new Delivery(1L, null, null, store);
=======
        Store store = new Store(1L, "Petlandia", "Avenida Lourenço Peixinho");
        Delivery delivery = new Delivery(1L, null, null, store,"Rua da Pega n13");
>>>>>>> 320026fcf8651a1b872487133b560c32ab5d9790

        JSONObject payload = new JSONObject();
        payload.put("delivery", "1");
        payload.put("rider", "randomemail@ua.pt");

        when(deliveryService.getDelivery(anyLong())).thenReturn(delivery);
        when(riderService.getRiderByEmail(anyString())).thenReturn(null);

        mvc.perform(put("/deliveries/deliveryRider")
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .content(String.valueOf(payload))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());

        verify(deliveryService, times(1)).getDelivery(anyLong());
        verify(riderService, times(1)).getRiderByEmail(anyString());
    }

    @Test
    void testSetDeliveryValidReview_thenStatus200() throws Exception {

<<<<<<< HEAD
        Store store = new Store(1L, "Petlandia", "Avenida Lourenço Peixinho n18");
        Delivery delivery = new Delivery(1L, null, null, store);
=======
        Store store = new Store(1L, "Petlandia", "Avenida Lourenço Peixinho");
        Delivery delivery = new Delivery(1L, null, null, store,"Rua da Pega n13");
>>>>>>> 320026fcf8651a1b872487133b560c32ab5d9790
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Review review = new Review(delivery, 4, timestamp);

        JSONObject payload = new JSONObject();
        payload.put("delivery", "1");
        payload.put("score", review.getScore().toString());
        payload.put("timestamp", timestamp.toString());

        when(deliveryService.getDelivery(anyLong())).thenReturn(delivery);
        when(reviewService.createReview(any(),anyInt(),any())).thenReturn(review);

        mvc.perform(put("/deliveries/deliveryReview")
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .content(String.valueOf(payload))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        verify(deliveryService, times(1)).getDelivery(anyLong());
        verify(reviewService, times(1)).createReview(any(),anyInt(),any());

    }

    @Test
    void testSetDeliveryInvalidReview_thenStatus400() throws Exception {

<<<<<<< HEAD
        Store store = new Store(1L, "Petlandia", "Avenida Lourenço Peixinho n18");
        Delivery delivery = new Delivery(1L, null, null, store);
=======
        Store store = new Store(1L, "Petlandia", "Avenida Lourenço Peixinho");
        Delivery delivery = new Delivery(1L, null, null, store,"Rua da Pega n13");
>>>>>>> 320026fcf8651a1b872487133b560c32ab5d9790
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Review review = new Review(delivery, 41212, timestamp);

        JSONObject payload = new JSONObject();
        payload.put("delivery", "1");
        payload.put("score", review.getScore().toString());
        payload.put("timestamp", timestamp.toString());

        when(deliveryService.getDelivery(anyLong())).thenReturn(delivery);
        when(reviewService.createReview(any(),anyInt(),any())).thenThrow(InvalidReviewException.class);

        mvc.perform(put("/deliveries/deliveryReview")
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .content(String.valueOf(payload))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());

        verify(deliveryService, times(1)).getDelivery(anyLong());
        verify(reviewService, times(1)).createReview(any(),anyInt(),any());

    }

    @Test
    void testGetDeliveries_thenStatus200() throws Exception {

        ArrayList<Delivery> deliveries = new ArrayList<>();
<<<<<<< HEAD
        deliveries.add(new Delivery(1L, null, null, new Store(1L, "Petlandia", "Avenida Lourenço Peixinho n18")));
        deliveries.add(new Delivery(2L, null, null, new Store(2L, "Petlandia", "Avenida Lourenço Peixinho n18")));
=======
        deliveries.add(new Delivery(1L, null, null, new Store(1L, "Petlandia", "Avenida Lourenço Peixinho"),"Rua da Pega n13"));
        deliveries.add(new Delivery(2L, null, null, new Store(2L, "Petlandia", "Avenida Lourenço Peixinho"),"Rua da Pega n13"));
>>>>>>> 320026fcf8651a1b872487133b560c32ab5d9790

        when(deliveryService.getAllDeliveries()).thenReturn(deliveries);

        mvc.perform(get("/deliveries/deliveries")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id", is(deliveries.get(0).getId().intValue())))
            .andExpect(jsonPath("$[0].rider", is(deliveries.get(0).getRider())))
            .andExpect(jsonPath("$[0].review", is(deliveries.get(0).getReview())))
            .andExpect(jsonPath("$[0].store.id", is(deliveries.get(0).getStore().getId().intValue())))
            .andExpect(jsonPath("$[0].store.name", is(deliveries.get(0).getStore().getName())))
            .andExpect(jsonPath("$[0].store.address", is(deliveries.get(0).getStore().getAddress())))
            .andExpect(jsonPath("$[1].id", is(deliveries.get(1).getId().intValue())))
            .andExpect(jsonPath("$[1].rider", is(deliveries.get(1).getRider())))
            .andExpect(jsonPath("$[1].review", is(deliveries.get(1).getReview())))
            .andExpect(jsonPath("$[1].store.id", is(deliveries.get(1).getStore().getId().intValue())))
            .andExpect(jsonPath("$[1].store.name", is(deliveries.get(1).getStore().getName())))
            .andExpect(jsonPath("$[1].store.address", is(deliveries.get(1).getStore().getAddress())));

        verify(deliveryService, times(1)).getAllDeliveries();

    }

    @Test
    void testGetDeliveriesByStatus_thenStatus200() throws Exception {

        ArrayList<Delivery> deliveries = new ArrayList<>();
<<<<<<< HEAD
        deliveries.add(new Delivery(1L, null, null, new Store(1L, "Petlandia", "Avenida Lourenço Peixinho n18")));
        deliveries.add(new Delivery(2L, null, null, new Store(2L, "Petlandia", "Avenida Lourenço Peixinho n18")));
=======
        deliveries.add(new Delivery(1L, null, null, new Store(1L, "Petlandia", "Avenida Lourenço Peixinho"),"Rua da Pega n13"));
        deliveries.add(new Delivery(2L, null, null, new Store(2L, "Petlandia", "Avenida Lourenço Peixinho"),"Rua da Pega n13"));
>>>>>>> 320026fcf8651a1b872487133b560c32ab5d9790

        when(deliveryService.getAllDeliveriesByStatus(any())).thenReturn(deliveries);

        mvc.perform(get("/deliveries/deliveriesByStatus")
            .param("status", "PENDING")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id", is(deliveries.get(0).getId().intValue())))
            .andExpect(jsonPath("$[0].rider", is(deliveries.get(0).getRider())))
            .andExpect(jsonPath("$[0].review", is(deliveries.get(0).getReview())))
            .andExpect(jsonPath("$[0].store.id", is(deliveries.get(0).getStore().getId().intValue())))
            .andExpect(jsonPath("$[0].store.name", is(deliveries.get(0).getStore().getName())))
            .andExpect(jsonPath("$[0].store.address", is(deliveries.get(0).getStore().getAddress())))
            .andExpect(jsonPath("$[1].id", is(deliveries.get(1).getId().intValue())))
            .andExpect(jsonPath("$[1].rider", is(deliveries.get(1).getRider())))
            .andExpect(jsonPath("$[1].review", is(deliveries.get(1).getReview())))
            .andExpect(jsonPath("$[1].store.id", is(deliveries.get(1).getStore().getId().intValue())))
            .andExpect(jsonPath("$[1].store.name", is(deliveries.get(1).getStore().getName())))
            .andExpect(jsonPath("$[1].store.address", is(deliveries.get(1).getStore().getAddress())));

        verify(deliveryService, times(1)).getAllDeliveriesByStatus(any());

    }

    
    @Test
    void testGetDeliveriesByInvalidStatus_thenStatus400() throws Exception {

        mvc.perform(get("/deliveries/deliveriesByStatus")
            .param("status", "PENDINGGGGGGGG")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());

    }

    @Test
    void testGetRiderDeliveries_thenStatus200() throws Exception {

        Rider rider = new Rider("chicodatina@gmail.com", "Rua da Concertina", "Chico da Tina");

        ArrayList<Delivery> deliveries = new ArrayList<>();
<<<<<<< HEAD
        deliveries.add(new Delivery(1L, null, null, new Store(1L, "Petlandia", "Avenida Lourenço Peixinho n18")));
        deliveries.add(new Delivery(2L, null, null, new Store(2L, "Petlandia", "Avenida Lourenço Peixinho n18")));
=======
        deliveries.add(new Delivery(1L, null, null, new Store(1L, "Petlandia", "Avenida Lourenço Peixinho"),"Rua da Pega n13"));
        deliveries.add(new Delivery(2L, null, null, new Store(2L, "Petlandia", "Avenida Lourenço Peixinho"),"Rua da Pega n13"));
>>>>>>> 320026fcf8651a1b872487133b560c32ab5d9790

        when(riderService.getRiderByEmail(any())).thenReturn(rider);
        when(deliveryService.getRiderDeliveries(any())).thenReturn(deliveries);

        mvc.perform(get("/deliveries/riderDeliveries")
            .param("email", rider.getEmail())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id", is(deliveries.get(0).getId().intValue())))
            .andExpect(jsonPath("$[0].rider", is(deliveries.get(0).getRider())))
            .andExpect(jsonPath("$[0].review", is(deliveries.get(0).getReview())))
            .andExpect(jsonPath("$[0].store.id", is(deliveries.get(0).getStore().getId().intValue())))
            .andExpect(jsonPath("$[0].store.name", is(deliveries.get(0).getStore().getName())))
            .andExpect(jsonPath("$[0].store.address", is(deliveries.get(0).getStore().getAddress())))
            .andExpect(jsonPath("$[1].id", is(deliveries.get(1).getId().intValue())))
            .andExpect(jsonPath("$[1].rider", is(deliveries.get(1).getRider())))
            .andExpect(jsonPath("$[1].review", is(deliveries.get(1).getReview())))
            .andExpect(jsonPath("$[1].store.id", is(deliveries.get(1).getStore().getId().intValue())))
            .andExpect(jsonPath("$[1].store.name", is(deliveries.get(1).getStore().getName())))
            .andExpect(jsonPath("$[1].store.address", is(deliveries.get(1).getStore().getAddress())));

        verify(riderService, times(1)).getRiderByEmail(any());
        verify(deliveryService, times(1)).getRiderDeliveries(any());

    }

    @Test
    void testGetInvalidRiderDeliveries_thenStatus400() throws Exception {

        when(riderService.getRiderByEmail(any())).thenReturn(null);

        mvc.perform(get("/deliveries/riderDeliveries")
            .param("email", "wdijwidiwjd@ua.pt")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());

        verify(riderService, times(1)).getRiderByEmail(any());

    }

    @Test
    void testGetStoreDeliveries_thenStatus200() throws Exception {

<<<<<<< HEAD
        Store store = new Store(1L, "Petlandia", "Avenida Lourenço Peixinho n18");

        ArrayList<Delivery> deliveries = new ArrayList<>();
        deliveries.add(new Delivery(1L, null, null, new Store(1L, "Petlandia", "Avenida Lourenço Peixinho n18")));
        deliveries.add(new Delivery(2L, null, null, new Store(2L, "Petlandia", "Avenida Lourenço Peixinho n18")));
=======
        Store store = new Store(1L, "Petlandia", "Avenida Lourenço Peixinho");

        ArrayList<Delivery> deliveries = new ArrayList<>();
        deliveries.add(new Delivery(1L, null, null, new Store(1L, "Petlandia", "Avenida Lourenço Peixinho"),"Rua da Pega n13"));
        deliveries.add(new Delivery(2L, null, null, new Store(2L, "Petlandia", "Avenida Lourenço Peixinho"),"Rua da Pega n13"));
>>>>>>> 320026fcf8651a1b872487133b560c32ab5d9790

        when(storeService.getStore(any())).thenReturn(store);
        when(deliveryService.getStoreDeliveries(any())).thenReturn(deliveries);

        mvc.perform(get("/deliveries/storeDeliveries")
            .param("id", "1")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id", is(deliveries.get(0).getId().intValue())))
            .andExpect(jsonPath("$[0].rider", is(deliveries.get(0).getRider())))
            .andExpect(jsonPath("$[0].review", is(deliveries.get(0).getReview())))
            .andExpect(jsonPath("$[0].store.id", is(deliveries.get(0).getStore().getId().intValue())))
<<<<<<< HEAD
            .andExpect(jsonPath("$[0].store.name", is(deliveries.get(0).getStore().getName())))            
=======
            .andExpect(jsonPath("$[0].store.name", is(deliveries.get(0).getStore().getName())))
>>>>>>> 320026fcf8651a1b872487133b560c32ab5d9790
            .andExpect(jsonPath("$[0].store.address", is(deliveries.get(0).getStore().getAddress())))
            .andExpect(jsonPath("$[1].id", is(deliveries.get(1).getId().intValue())))
            .andExpect(jsonPath("$[1].rider", is(deliveries.get(1).getRider())))
            .andExpect(jsonPath("$[1].review", is(deliveries.get(1).getReview())))
            .andExpect(jsonPath("$[1].store.id", is(deliveries.get(1).getStore().getId().intValue())))
            .andExpect(jsonPath("$[1].store.name", is(deliveries.get(1).getStore().getName())))
            .andExpect(jsonPath("$[1].store.address", is(deliveries.get(1).getStore().getAddress())));

        verify(storeService, times(1)).getStore(any());
        verify(deliveryService, times(1)).getStoreDeliveries(any());

    }

    @Test
    void testGetInvalidStoreDeliveries_thenStatus400() throws Exception {

        when(storeService.getStore(any())).thenReturn(null);

        mvc.perform(get("/deliveries/storeDeliveries")
            .param("id", "101145")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());

        verify(storeService, times(1)).getStore(any());

    }

}