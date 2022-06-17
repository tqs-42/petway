package com.specific.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.Map;
import com.specific.model.Client;
import com.specific.service.ClientService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService service;

    @PostMapping("/add")
    public ResponseEntity<Client> registerClient(@RequestBody Map<String, String> data) throws Exception {

        String email = data.get("email");
        String address = data.get("address");
        String fullname = data.get("fullname");
        String password = data.get("password");

        Client client = service.saveClient(new Client(email, password, fullname, address));
        return new ResponseEntity<>(client, HttpStatus.CREATED);

    }
}