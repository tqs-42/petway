package com.specific.controller;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.specific.exception.ConflictException;
import com.specific.exception.ResourceNotFoundException;
import com.specific.model.RequestProducts;
import com.specific.service.CartService;

@RestController
@CrossOrigin(origins = { "http://localhost:4201", "http://0.0.0.0:6868"})
@RequestMapping("/carts")
public class CartController {
    private static final String EMAIL = "clientEmail ";
    private static final String NOTFOUND = ", NOT FOUND.";

    @Autowired
    private CartService service;

    @GetMapping("/user/{email}/product/{productId}")
    public ResponseEntity<RequestProducts> getProductAmout(@Valid @PathVariable String email,
            @Valid @PathVariable Long productId) throws ResourceNotFoundException {
        RequestProducts requestProducts = service.getProductAmout(email, productId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        EMAIL + email + ", productID " + productId + NOTFOUND));
        return ResponseEntity.ok().body(requestProducts);
    }

    @PutMapping("/user/{email}/product/{productId}/amount/{amount}")
    public ResponseEntity<RequestProducts> putProductAmout(@Valid @PathVariable String email,
            @Valid @PathVariable Long productId, @Valid @PathVariable int amount)
            throws ResourceNotFoundException, ConflictException {
        Optional<RequestProducts> requestProductsOptional = service.putProductAmout(email, productId, amount);
        RequestProducts requestProducts = requestProductsOptional.orElseThrow(() -> new ResourceNotFoundException(
                EMAIL + email + ", productID " + productId + NOTFOUND));
        requestProducts = requestProductsOptional.orElseThrow(
                () -> new ConflictException("[ERROR] The products in the cart must be from the same store!"));
        return ResponseEntity.ok().body(requestProducts);
    }

    @GetMapping("/user/{email}/products")
    public ResponseEntity<List<RequestProducts>> getProducts(@Valid @PathVariable String email)
            throws ResourceNotFoundException {
        List<RequestProducts> requestsProducts = service.getProducts(email)
                .orElseThrow(() -> new ResourceNotFoundException(EMAIL + email + NOTFOUND));
        return ResponseEntity.ok().body(requestsProducts);
    }
}
