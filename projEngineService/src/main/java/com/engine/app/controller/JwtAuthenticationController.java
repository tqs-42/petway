package com.engine.app.controller;

import java.util.Map;

import javax.annotation.PostConstruct;

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
import com.engine.app.model.Admin;
import com.engine.app.model.JwtRequest;
import com.engine.app.model.JwtResponse;
import com.engine.app.model.Rider;
import com.engine.app.service.AdminService;
import com.engine.app.service.JwtUserDetailsService;
import com.engine.app.service.RiderService;


@RequestMapping
@RestController
@CrossOrigin("http://localhost:4200")
public class JwtAuthenticationController {

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private RiderService riderService;

    @Autowired
    private AdminService adminService;

    Logger logger = LoggerFactory.getLogger(JwtAuthenticationController.class);

    @PostConstruct
    public void init() throws ConflictException {
        adminService.registerAdmin("admin@ua.pt", "admin123", "Avenida Lourenço Peixinho", "Bernardo Melo");
    }

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

}