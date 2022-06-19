package com.specific.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.specific.model.RequestProducts;
import com.specific.service.CartService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/carts")
public class CartController {
    @Autowired
    private CartService service;

    @GetMapping("/user/{email}/product/{productId}")
    public ResponseEntity<RequestProducts> getProductAmout(@Valid @PathVariable String email, @Valid @PathVariable Long productId) {
        try {
            return ResponseEntity.ok(service.getProductAmout(email, productId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    
    @PutMapping("/user/{email}/product/{productId}/amount/{amount}")
    public ResponseEntity<RequestProducts> putProductAmout(@Valid @PathVariable String email, @Valid @PathVariable Long productId, @Valid @PathVariable int amount) {
        try {
            return ResponseEntity.ok(service.putProductAmout(email, productId, amount));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
