package com.specific.controller;

import java.util.List;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.specific.exception.ResourceNotFoundException;
import com.specific.model.RequestProducts;
import com.specific.service.CartService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/carts")
public class CartController {
    @Autowired
    private CartService service;

    @GetMapping("/user/{email}/product/{productId}")
    public ResponseEntity<RequestProducts> getProductAmout(@Valid @PathVariable String email, @Valid @PathVariable Long productId)  throws ResourceNotFoundException {
        RequestProducts requestProducts = service.getProductAmout(email, productId).orElseThrow(() -> new ResourceNotFoundException("clientEmail " + email + ", productID " + productId + ", NOT FOUND."));
        return ResponseEntity.ok().body(requestProducts);
    }

    
    @PutMapping("/user/{email}/product/{productId}/amount/{amount}")
    public ResponseEntity<RequestProducts> putProductAmout(@Valid @PathVariable String email, @Valid @PathVariable Long productId, @Valid @PathVariable int amount) {
        try {
            return ResponseEntity.ok(service.putProductAmout(email, productId, amount));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/user/{email}/products")
    public ResponseEntity<List<RequestProducts>> getProducts(@Valid @PathVariable String email) throws ResourceNotFoundException {
        List<RequestProducts> requestsProducts = service.getProducts(email).orElseThrow(() -> new ResourceNotFoundException("clientEmail " + email + ", NOT FOUND."));
        return ResponseEntity.ok().body(requestsProducts);
    }
}
