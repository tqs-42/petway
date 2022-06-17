package com.engine.app.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.engine.app.exception.ConflictException;
import com.engine.app.exception.ResourceNotFoundException;
import com.engine.app.model.Store;
import com.engine.app.service.StoreService;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/stores")
public class StoreController {

    @Autowired
    private StoreService storeService;
    
    @PostMapping("/add-store")
    public ResponseEntity<String> addStore(@RequestBody Map<String, String> data) throws Exception {
        try {
            storeService.addStore(data.get("name"), data.get("address"));
        } catch (ConflictException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-all-stores")
    public ResponseEntity<List<Store>> getStoreByName() throws ResourceNotFoundException {
        try {
            return ResponseEntity.ok(storeService.getAllStores());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    
}
