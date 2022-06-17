package com.specific.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
}
