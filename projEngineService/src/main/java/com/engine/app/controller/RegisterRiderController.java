package com.engine.app.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

import com.engine.app.repository.RiderRepository;
import com.engine.app.model.Rider;
import com.engine.app.service.RegisterRiderService;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/registerRider")
public class RegisterRiderController {
    
    @AutoWired
    private RegisterRiderService registerRiderService;

    @PostMapping("/email/{email}/address/{address}/fullname/{fullname}/password/{password}")
    public ResponseEntity<Rider> getCovidDataByCountry(@PathVariable String email, @PathVariable String address, @PathVariable String fullname, @PathVariable String password) {
        return ResponseEntity.ok(registerRiderService.registerRider(email, password, address, fullname));
    }


}
