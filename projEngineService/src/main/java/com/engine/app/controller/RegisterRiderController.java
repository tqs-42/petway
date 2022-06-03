package com.engine.app.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

import com.engine.app.repository.RiderRepository;
import com.engine.app.model.Rider;
import com.engine.app.exception.ConflictException;
import com.engine.app.model.Person;
import com.engine.app.service.RegisterRiderService;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/registerRider")
public class RegisterRiderController {
    
    @Autowired
    private RegisterRiderService registerRiderService;

    @PostMapping("/email/{email}/address/{address}/fullname/{fullname}/password/{password}")
    public ResponseEntity<String> getCovidDataByCountry(@PathVariable String email, @PathVariable String address, @PathVariable String fullname, @PathVariable String password) {
        try {
            registerRiderService.registerRider(email, password, address, fullname);
        } catch (ConflictException e) {
            return ResponseEntity.badRequest().body("Rider not registered " + e.getMessage());
        }
        return ResponseEntity.ok("Rider registered successfully");    
    }
    
    @GetMapping("/email_get/{email}")
    public Person getRiderByEmail(@PathVariable String email) {
        return registerRiderService.getRiderByEmail(email);
    }

}
 