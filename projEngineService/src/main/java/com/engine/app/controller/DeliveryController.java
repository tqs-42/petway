package com.engine.app.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.engine.app.model.Review;
import com.engine.app.model.Rider;
import com.engine.app.model.Store;
import com.engine.app.service.DeliveryService;
import com.engine.app.service.EventService;
import com.engine.app.service.ReviewService;
import com.engine.app.service.RiderService;
import com.engine.app.service.StoreService;

@RestController
@RequestMapping("/deliveries")
@CrossOrigin(origins = {"http://localhost:4200","http://192.168.160.234:4200"})
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
    public ResponseEntity<Delivery> getDelivery(@RequestParam() Long id) throws ResourceNotFoundException {
        Delivery delivery = deliveryService.getDelivery(id);
        if (delivery == null) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(delivery);
    }

    @PostMapping("/delivery")
    public ResponseEntity<Delivery> createDelivery(@RequestBody Map<String, String> data) throws Exception {
        Store store = storeService.getStore(Long.valueOf(data.get("id")));
        if (store == null) {
            return ResponseEntity.badRequest().body(null);
        }
        Delivery delivery = deliveryService.createDelivery(store);
        eventService.createEvent(delivery, DeliveryStatus.PENDING);
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
    public ResponseEntity<List<Delivery>> getAllDeliveriesByStatus(@RequestParam() String status) {
        DeliveryStatus deliveryStatus;
        try {
            deliveryStatus = DeliveryStatus.valueOf(status);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(null);
        }
        List<Delivery> deliveries = deliveryService.getAllDeliveriesByStatus(deliveryStatus);
        return ResponseEntity.ok(deliveries);
    }

    @GetMapping("/riderDeliveries")
    public ResponseEntity<List<Delivery>> getRiderDeliveries(@RequestParam() String email) throws NumberFormatException, ResourceNotFoundException {
        Rider rider = riderService.getRiderByEmail(email);
        if (rider == null) {
            return ResponseEntity.badRequest().body(null);
        }
        List<Delivery> deliveries = deliveryService.getRiderDeliveries(rider);
        return ResponseEntity.ok(deliveries);
    }

    @GetMapping("/storeDeliveries")
    public ResponseEntity<List<Delivery>> getStoreDeliveries(@RequestParam() Long id) throws NumberFormatException, ResourceNotFoundException {
        Store store = storeService.getStore(id);
        if (store == null) {
            return ResponseEntity.badRequest().body(null);
        }
        List<Delivery> deliveries = deliveryService.getStoreDeliveries(store);
        return ResponseEntity.ok(deliveries);
    }

}
