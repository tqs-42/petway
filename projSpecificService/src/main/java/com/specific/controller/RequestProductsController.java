package com.specific.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.specific.model.RequestProducts;
import com.specific.service.RequestProductsService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/specific")
public class RequestProductsController {
    @Autowired
    private RequestProductsService service;

    @PostMapping("/addRequestProducts")
    public RequestProducts addRequestProducts(@RequestBody RequestProducts requestProducts) {
        return service.saveRequestProducts(requestProducts);
    }

    @PostMapping("/addRequestsProducts")
    public List<RequestProducts> addRequestsProducts(@RequestBody List<RequestProducts> requestsProducts) {
        return service.saveRequestsProducts(requestsProducts);
    }

    @GetMapping("/requestsProducts")
    public List<RequestProducts> findAllRequestsProducts() {
        return service.getRequestsProducts();
    }

    @GetMapping("/requestProductsById/{id}")
    public RequestProducts findRequestProductsById(@PathVariable int id) {
        return service.getRequestProductsById(id);
    }

    @PutMapping("/updateRequestProducts")
    public RequestProducts updateRequestProducts(@RequestBody RequestProducts requestProducts) {
        return service.updateRequestProducts(requestProducts);
    }

    @DeleteMapping("/deleteRequestProducts/{id}")
    public String deleteRequestProducts(@PathVariable int id) {
        return service.deleteRequestProducts(id);
    }
}
