package com.specific.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.specific.exception.ConflictException;
import com.specific.model.Client;
import com.specific.repository.ClientRepository;

@Service
public class ClientService {


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ClientRepository repository;

    public Client saveClient(Client client) throws ConflictException {
        if (repository.findByEmail(client.getEmail()) == null) {
            client.setPassword(passwordEncoder.encode(client.getPassword()));
            repository.saveAndFlush(client);
            return client;

        } else {
            throw new ConflictException("Client already exists");
        }
    }

}
