package com.specific.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.engine.app.exception.ConflictException;
import com.specific.model.Client;
import com.specific.repository.ClientRepository;

@Service
public class ClientService {
    @Autowired
    private ClientRepository repository;

    public Client saveClient(Client client) throws Exception {
        if (repository.findByEmail(client.getEmail()) == null) {
            repository.saveAndFlush(client);
            return client;

        } else {
            throw new Exception();
        }
    }

    public List<Client> saveClients(List<Client> clients) {
        return repository.saveAll(clients);
    }

    public List<Client> getClients() {
        return repository.findAll();
    }

    public Client getClientByEmail(String email) {
        return repository.findByEmail(email);
    }

    public String deleteClient(String email) {
        repository.deleteByEmail(email);
        return "client removed !! " + email;
    }

    // public Client updateClient(Client client) {
    // Client existingClient = repository.findByEmail(client.getEmail());
    // existingClient.setPassword(client.getPassword());
    // existingClient.setFullname(client.getFullname());
    // existingClient.setAddresses(client.getAddresses());
    // existingClient.setCart(client.getCart());
    // return repository.save(existingClient);
    // }
}
