package com.specific.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.specific.model.Client;
import com.specific.service.ClientService;

@RestController
@RequestMapping("/api/specific")
public class ClientController {

    @Autowired
    private ClientService service;

    @PostMapping("/addClient")
    public Client addClient(@RequestBody Client client) {
        return service.saveClient(client);
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

    @PutMapping("/updateClient")
    public Client updateClient(@RequestBody Client client) {
        return service.updateClient(client);
    }

    @DeleteMapping("/deleteClient/{email}")
    public String deleteClient(@PathVariable String email) {
        return service.deleteClient(email);
    }   
}
