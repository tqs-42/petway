package com.specific.controller;

import java.util.Set;
import java.util.Map;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.specific.model.Product;
import com.specific.model.RequestEvents;
import com.specific.model.RequestProducts;
import com.specific.service.CartService;
import com.specific.service.RequestEventsService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/requestEvents")
public class RequestEventsController {

    @Autowired
    private RequestEventsService service;

    @GetMapping("/{email}")
    public ResponseEntity<Set<RequestEvents>> getAllRequestEvents(@Valid @PathVariable String email) {
        try {
            return ResponseEntity.ok(service.getAllRequestEvents(email));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<RequestEvents> getRequestEvents(@Valid @PathVariable long id) {
        try {
            return ResponseEntity.ok(service.getRequestEvents(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/products/{orderId}")
    public ResponseEntity<List<Map<String, Object>>> getProductsInfoInRequestEvent(@Valid @PathVariable long orderId) {
        try {
            return ResponseEntity.ok(service.getProductsInfoByOrderId(orderId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

}
