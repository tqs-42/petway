package com.specific.controller;

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

import com.specific.exception.ConflictException;
import com.specific.exception.InvalidCredentialsException;
import com.specific.model.JwtRequest;
import com.specific.model.JwtResponse;
import com.specific.service.JwtUserDetailsService;
import com.specific.model.Client;
import com.specific.model.Manager;
import com.specific.model.Store;
import com.specific.service.ClientService;
import com.specific.service.ManagerService;
import com.specific.service.StoreService;

@RequestMapping
@RestController
@CrossOrigin(origins = { "http://192.168.160.234:4201", "http://localhost:4201" })
public class JwtAuthenticationController {

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ManagerService managerService;

    @Autowired
    private StoreService storeService;

    Logger logger = LoggerFactory.getLogger(JwtAuthenticationController.class);

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
            throws InvalidCredentialsException, UsernameNotFoundException {

        return ResponseEntity.ok(userDetailsService.generateTokenLogin(authenticationRequest));

    }

    @PostMapping("/registerClient")
    public ResponseEntity<Client> registerClient(@RequestBody Map<String, String> data) throws ConflictException {
        Client client;
        try {
            client = clientService.saveClient(
                    new Client(data.get("email"), data.get("password"), data.get("fullname"), data.get("address")));
        } catch (ConflictException e) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(client);
    }

    @PostMapping("/registerManager")
    public ResponseEntity<Manager> registerManager(@RequestBody Map<String, String> data) throws ConflictException  {
        Store store;
        try {
            store = storeService.saveStore(new Store(data.get("name"), data.get("address"), true));
        } catch (ConflictException e) {
            return ResponseEntity.badRequest().body(null);
        }
        Manager manager;
        try {
            manager = managerService
                    .saveManager(new Manager(data.get("email"), data.get("password"), data.get("fullname"), store));
        } catch (ConflictException e) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(manager);
    }

}