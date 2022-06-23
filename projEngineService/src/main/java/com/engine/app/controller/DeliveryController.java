package com.engine.app.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
import com.engine.app.service.ReviewService;
import com.engine.app.service.RiderService;
import com.engine.app.service.StoreService;
import com.engine.app.exception.ResourceNotFoundException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;




@RestController
@RequestMapping("/deliveries")
@CrossOrigin(origins = {"http://localhost:4200","http://localhost:19006","http://localhost:6868", "http://localhost:6869",  "http://0.0.0.0:6869"})
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private EventService eventService;

    @Autowired
    private RiderService riderService;

    @Autowired
    private StoreService storeService;

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/delivery")
    public ResponseEntity<Map<String,Object>> getDelivery(@RequestParam() Long id) throws ResourceNotFoundException {
        Map<String,Object> map = new HashMap<>();
        Delivery delivery = deliveryService.getDelivery(id);
        if (delivery == null) {
            return ResponseEntity.badRequest().body(null);
        }
        Event event = eventService.getDeliveryEvent(delivery);
        map.put("delivery", delivery);
        map.put("event", event);
        return ResponseEntity.ok(map);
    }

    @PostMapping("/delivery")
    public ResponseEntity<Delivery> createDelivery(@RequestBody Map<String, String> data) throws Exception {
        System.out.println("received delivery");
        Store store = storeService.getStore(data.get("store"));
        if (store == null) {
            return ResponseEntity.badRequest().body(null);
        }
        System.out.println("store");
        System.out.println(store.getName());
        Delivery delivery = deliveryService.createDelivery(store, data.get("address"));
        eventService.createEvent(delivery, DeliveryStatus.PENDING);
        System.out.println("okkkkkkkkkkkkkkkkkkkkk");
        return ResponseEntity.ok(delivery);
    }

    @PostMapping("/deliveryUpdate")
    public ResponseEntity<Delivery> updateDelivery(@RequestBody Map<String, String> data) throws Exception {
        Delivery delivery = deliveryService.getDelivery(Long.valueOf(data.get("id")));
        if (delivery == null) {
            return ResponseEntity.badRequest().body(null);
        }
        if (delivery.getRider() == null) {
            Rider rider = riderService.getRiderByEmail(data.get("email"));
            deliveryService.setDeliveryRider(delivery, rider);
        }
        Event event = eventService.createEvent(delivery, DeliveryStatus.valueOf(data.get("status")));
        return ResponseEntity.ok(delivery);
    }


    @PutMapping("/deliveryRider")
    public ResponseEntity<Delivery> setDeliveryRider(@RequestBody Map<String, String> data) throws NumberFormatException, ResourceNotFoundException {
        Delivery delivery = deliveryService.getDelivery(Long.valueOf(data.get("delivery")));
        if (delivery == null) {
            return ResponseEntity.badRequest().body(null);
        }
        Rider rider = riderService.getRiderByEmail(data.get("rider"));
        if (rider == null) {
            return ResponseEntity.badRequest().body(null);
        }
        delivery = deliveryService.setDeliveryRider(delivery, rider);
        return ResponseEntity.ok(delivery);
    }

    @PutMapping("/deliveryReview")
    public ResponseEntity<Delivery> setDeliveryReview(@RequestBody Map<String, String> data) throws NumberFormatException, ResourceNotFoundException, ParseException, InvalidReviewException {
        Delivery delivery = deliveryService.getDelivery(Long.valueOf(data.get("delivery")));
        if (delivery == null) {
            return ResponseEntity.badRequest().body(null);
        }
        Review review;
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS").parse(data.get("timestamp"));
            Timestamp timestamp = new Timestamp(date.getTime());
            review = reviewService.createReview(delivery, Integer.parseInt(data.get("score")), timestamp);
        } catch (InvalidReviewException e) {
            return ResponseEntity.badRequest().body(null);
        }
        delivery = deliveryService.setDeliveryReview(delivery, review);
        return ResponseEntity.ok(delivery);
    }

    @GetMapping("/deliveries")
    public ResponseEntity<List<Delivery>> getAllDeliveries() {
        return ResponseEntity.ok(deliveryService.getAllDeliveries());
    }

    @GetMapping("/deliveriesByStatus")
    public ResponseEntity<List<Map<String,Object>>> getAllDeliveriesByStatus(@RequestParam() String status) {
        List<Map<String,Object>> deliveries = new ArrayList<>();
        DeliveryStatus deliveryStatus;
        try {
            deliveryStatus = DeliveryStatus.valueOf(status);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(null);
        }
        for (Delivery d : deliveryService.getAllDeliveriesByStatus(deliveryStatus)) {
            Map<String,Object> map = new HashMap<>();
            map.put("id",d.getId());
            map.put("store_name",d.getStore().getName());
            map.put("store_address",d.getStore().getAddress());
            deliveries.add(map);
        }
        System.out.println("porto");
        System.out.println(deliveries);
        return ResponseEntity.ok(deliveries);
    }

    @GetMapping("/riderDeliveries")
    public ResponseEntity<List<Map<String,Object>>> getRiderDeliveries(@RequestParam() String email) throws NumberFormatException, ResourceNotFoundException {
        List<Map<String,Object>> deliveries = new ArrayList<>();
        Rider rider = riderService.getRiderByEmail(email);
        if (rider == null) {
            return ResponseEntity.badRequest().body(null);
        }
        for (Delivery d : deliveryService.getRiderDeliveries(rider)) {
            Map<String,Object> map = new HashMap<>();
            map.put("id",d.getId());
            map.put("store_name",d.getStore().getName());
            map.put("store_address",d.getStore().getAddress());
            deliveries.add(map);
        }
        return ResponseEntity.ok(deliveries);
    }

    @GetMapping("/storeDeliveries")
    public ResponseEntity<List<Delivery>> getStoreDeliveries(@RequestParam() String name) throws NumberFormatException, ResourceNotFoundException {
        Store store = storeService.getStore(name);
        if (store == null) {
            return ResponseEntity.badRequest().body(null);
        }
        List<Delivery> deliveries = deliveryService.getStoreDeliveries(store);
        return ResponseEntity.ok(deliveries);
    }

}
