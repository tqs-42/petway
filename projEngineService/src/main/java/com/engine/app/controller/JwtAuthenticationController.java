package com.engine.app.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.engine.app.config.JwtTokenUtil;
import com.engine.app.exception.ConflictException;
import com.engine.app.model.JwtRequest;
import com.engine.app.model.JwtResponse;
import com.engine.app.service.JwtUserDetailsService;
import com.engine.app.service.RiderService;


@RequestMapping
@RestController
@CrossOrigin("http://localhost:4200")
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

        authenticate(authenticationRequest);
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
		final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token, userDetails.getAuthorities().iterator().next(), userDetails.getUsername()));

    }

    @PostMapping("/register")
    public ResponseEntity<String> registerRider(@RequestBody Map<String,String> data) throws Exception {
        try {
            riderService.registerRider(data.get("email"), data.get("password"), data.get("address"), data.get("fullname"));
        } catch (ConflictException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    private void authenticate(JwtRequest jwtRequest) throws Exception {
        try {
            System.out.println(jwtRequest.getEmail() + " | " + jwtRequest.getPassword());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getEmail(), jwtRequest.getPassword()));
        } catch (BadCredentialsException e) {
            System.out.println("BAD CREDENTIALS");
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}