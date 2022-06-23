package com.engine.app.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.engine.app.exception.ConflictException;
import com.engine.app.exception.InvalidCredentialsException;
import com.engine.app.model.JwtRequest;
import com.engine.app.model.JwtResponse;
import com.engine.app.model.Rider;
import com.engine.app.service.JwtUserDetailsService;
import com.engine.app.service.RiderService;
import com.engine.app.model.Person;
import com.engine.app.service.PersonService;


@RequestMapping
@RestController
@CrossOrigin(origins = {"http://localhost:4200","http://localhost:19006","http://localhost:6868", "http://0.0.0.0:6869"})
public class JwtAuthenticationController {

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private RiderService riderService;

    @Autowired
    private PersonService personService;


    Logger logger = LoggerFactory.getLogger(JwtAuthenticationController.class);

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws InvalidCredentialsException, UsernameNotFoundException {

        return ResponseEntity.ok(userDetailsService.generateTokenLogin(authenticationRequest));

    }

    @PostMapping("/register")
    public ResponseEntity<Rider> registerRider(@RequestBody Map<String,String> data) throws Exception {
        Rider rider;
        try {
            rider = riderService.registerRider(data.get("email"), data.get("password"), data.get("address"), data.get("fullname"));
        } catch (ConflictException e) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(rider);
    }

    @PostMapping("/registerUser")
    public ResponseEntity<Person> registerUser(@RequestBody Map<String,String> data) throws Exception {
        Person person;
        try {
            person = personService.registerPerson(data.get("email"), data.get("password"), data.get("address"), data.get("fullname"));
        } catch (ConflictException e) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(person);
    }

}