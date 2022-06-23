package com.specific.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.specific.exception.ResourceNotFoundException;
import com.specific.service.RequestProductsService;

@RestController
@CrossOrigin(origins =  {"http://localhost:4201", "http://0.0.0.0:6868"})
@RequestMapping("/requestesProducts")
public class RequestProductsController {
    @Autowired
    private RequestProductsService service;

    @DeleteMapping("/delete/{userEmail}")
    public ResponseEntity<String> deleteRequestProducts(@Valid @PathVariable String userEmail) throws Exception {
        System.out.println("!!!!!!!!!!DELETE!!!!!!!!!!!!!!!!!!");
        try {
            service.deleteRequestProducts(userEmail);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.noContent().build();
    }
}