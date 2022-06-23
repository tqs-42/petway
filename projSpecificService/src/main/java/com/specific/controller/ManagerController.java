package com.specific.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.specific.exception.ResourceNotFoundException;
import com.specific.model.Store;
import com.specific.service.ManagerService;

@RestController
@CrossOrigin(origins = "http://localhost:4201")
@RequestMapping("/managers")
public class ManagerController {

    @Autowired
    private ManagerService service;

    @GetMapping("/user/{email}/store")
    public Map<String, String> getStore(@Valid @PathVariable String email) throws ResourceNotFoundException {
        Store store = service.getStore(email).orElseThrow(() -> new ResourceNotFoundException("managerEmail " + email + ", or store NOT FOUND."));
        Map<String, String> data = new HashMap<>();
        data.put("id", String.valueOf(store.getId()));
        data.put("name", store.getName());
        data.put("address", store.getAddress());
        return data;
    }
}
