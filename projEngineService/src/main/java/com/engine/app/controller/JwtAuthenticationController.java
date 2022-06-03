package com.engine.app.controller;

import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.engine.app.config.JwtTokenUtil;
import com.engine.app.model.JwtRequest;
import com.engine.app.model.JwtResponse;
import com.engine.app.model.Rider;
import com.engine.app.service.JwtUserDetailsService;
import com.engine.app.service.RiderService;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private RiderService riderService;

    Logger logger = LoggerFactory.getLogger(JwtAuthenticationController.class);

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));

    }

    @PostMapping("/register")
    public ResponseEntity<Rider> registerRider(@RequestBody Map<String,String> data) throws Exception {

        logger.info("Register");

        System.out.println("uiiiiiiiiiiiii");

        System.out.println(data);

        String email = data.get("email");
        String address = data.get("address");
        String fullname = data.get("fullname");
        String password = data.get("password");

        Rider rider = riderService.save(new Rider(email, address, fullname, password, true));
        return new ResponseEntity<>(rider, HttpStatus.CREATED);

        // Rider rider = new Rider(email, address, fullname, password, true);
        // try {
        //     rider = riderService.save(rider);
        // } catch (Exception e) {
        //     // Already exists a rider with those fields
        //     return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        // }

        // System.out.println("Rider saved");

        // authenticate(rider.getEmail(), rider.getPassword());
        // final UserDetails userDetails = userDetailsService.loadUserByUsername(rider.getEmail());
        // final String token = jwtTokenUtil.generateToken(userDetails);
        // return ResponseEntity.ok(new JwtResponse(token));
        
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}