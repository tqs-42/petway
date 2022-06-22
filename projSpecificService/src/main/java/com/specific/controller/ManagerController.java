package com.specific.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.specific.exception.ResourceNotFoundException;
import com.specific.model.Store;
import com.specific.service.ManagerService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/manageres")
public class ManagerController {

    @Autowired
    private ManagerService service;

    @GetMapping("/user/{email}/store")
    public ResponseEntity<Store> getStore(@Valid @PathVariable String email) throws ResourceNotFoundException {
        Store store = service.getStore(email).orElseThrow(() -> new ResourceNotFoundException("managerEmail " + email + ", or store NOT FOUND."));
        return ResponseEntity.ok().body(store);
    }
}
