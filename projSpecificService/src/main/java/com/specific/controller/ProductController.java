package com.specific.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import com.specific.exception.ConflictException;
import com.specific.model.Product;
import com.specific.service.ProductService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
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
}
