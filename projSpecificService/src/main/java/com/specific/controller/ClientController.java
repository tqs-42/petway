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
@RequestMapping("/api/specific")
public class ClientController {

    @Autowired
    private ClientService service;

    @PostMapping("/addClient")
    public ResponseEntity<Client> registerClient(@RequestBody Map<String, String> data) throws Exception {

        String email = data.get("email");
        String address = data.get("address");
        String fullname = data.get("fullname");
        String password = data.get("password");

        Client client = service.saveClient(new Client(email, password, fullname, address));
        return new ResponseEntity<>(client, HttpStatus.CREATED);

    }

    @PostMapping("/addClients")
    public List<Client> addClients(@RequestBody List<Client> clients) {
        return service.saveClients(clients);
    }

    @GetMapping("/clients")
    public List<Client> findAllClients() {
        return service.getClients();
    }

    @GetMapping("/clientByEmail/{email}")
    public Client findClientByEmail(@PathVariable String email) {
        return service.getClientByEmail(email);
    }

    // @PutMapping("/updateClient")
    // public Client updateClient(@RequestBody Client client) {
    // return service.updateClient(client);
    // }

    @DeleteMapping("/deleteClient/{email}")
    public String deleteClient(@PathVariable String email) {
        return service.deleteClient(email);
    }
}