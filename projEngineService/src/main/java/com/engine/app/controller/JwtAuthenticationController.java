package com.engine.app.controller;

import java.util.Map;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody Map<String,String> data) throws Exception {

        System.out.println("LOGIN");

        String email = data.get("email");
        String password = data.get("password");

        System.out.println(email);
        System.out.println(password);


        authenticate(email, password);

        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        final UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        System.out.println(userDetails);

        final String token = jwtTokenUtil.generateToken(userDetails);

        System.out.println(token);


        System.out.println("LOGIN OK!!!!!!!!!!!!!!");

        return ResponseEntity.ok(new JwtResponse(token));

    }

    @PostMapping("/register")
    public ResponseEntity<Rider> registerRider(@RequestBody Map<String,String> data) throws Exception {

        String email = data.get("email");
        String address = data.get("address");
        String fullname = data.get("fullname");
        String password = data.get("password");

        Rider rider = riderService.save(new Rider(email, address, fullname, password, true));
        return new ResponseEntity<>(rider, HttpStatus.CREATED);
        
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            System.out.println("tryyyyyy");
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            System.out.println("0000000");
        } catch (DisabledException e) {
            System.out.println("1");
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            System.out.println("1");
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}