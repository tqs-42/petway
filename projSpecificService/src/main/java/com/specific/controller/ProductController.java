package com.specific.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.specific.exception.ConflictException;
import com.specific.model.Product;
import com.specific.service.ProductService;

@RestController
@CrossOrigin(origins =  {"http://localhost:4201", "http://0.0.0.0:6868"})
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService service;

    @PostMapping("/add")
    public Product addProduct(@RequestBody Map<String, String> data) throws ConflictException {
        try {
            return service.saveProduct(data);
        } catch (Exception e) {
            throw new ConflictException("Product already exists");
        }
    }

    @GetMapping("")
    public List<Product> findAllProducts() {
        return service.getProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findProductById(@Valid @PathVariable Long id) {
        try {
            Product product = service.getProductById(id);
            if (product == null) {
                return ResponseEntity.badRequest().body(null);
            }
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
