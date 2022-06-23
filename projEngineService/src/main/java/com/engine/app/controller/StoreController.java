package com.engine.app.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.engine.app.exception.ConflictException;
import com.engine.app.exception.ResourceNotFoundException;
import com.engine.app.model.Delivery;
import com.engine.app.model.Store;
import com.engine.app.service.StoreService;

@RestController
@RequestMapping("/stores")
@CrossOrigin(origins = {"http://localhost:4200","http://localhost:19006","http://localhost:6868", "http://localhost:6869",  "http://0.0.0.0:6869"})
public class StoreController {

    @Autowired
    private StoreService storeService;
    
    @PostMapping("/store")
    public ResponseEntity<Store> addStore(@RequestBody Map<String, String> data) throws Exception {
        Store store;
        try {
            store = storeService.addStore(data.get("name"), data.get("address"));
        } catch (ConflictException e) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(store);
    }

    @DeleteMapping("/store")
    public ResponseEntity<String> deleteStore(@RequestParam Long id) throws Exception {
        try {
            storeService.deleteStore(id);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/store")
    public ResponseEntity<Store> getStore(@RequestParam String name) throws Exception {
        Store store;
        try {
            store = storeService.getStore(name);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(store);
    }
    
    @GetMapping("/totalStores")
    public ResponseEntity<Integer> getTotalStores() throws Exception {
        try {
            return ResponseEntity.ok(storeService.getAllStores().size());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/stores")
    public ResponseEntity<List<Store>> getStores() throws ResourceNotFoundException {
        try {
            return ResponseEntity.ok(storeService.getAllStores());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    
}
