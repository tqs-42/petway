package com.specific.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.specific.exception.ConflictException;
import com.specific.exception.ResourceNotFoundException;
import com.specific.model.Cart;
import com.specific.model.Client;
import com.specific.model.Request;
import com.specific.repository.ClientRepository;
import com.specific.repository.RequestRepository;

@Service
public class RequestService {
    @Autowired
    private RequestRepository repository;

    @Autowired
    private ClientRepository clientRepository;

    public Request addRequest(String userEmail) throws ResourceNotFoundException, ConflictException {
        Client client = clientRepository.findByEmail(userEmail);
        System.out.println(client);
        if (client == null) {
            throw new ResourceNotFoundException("Client " + userEmail + " not found!");
        } else {
            System.out.println("Vamos ");
            String address = client.getAddress();
            System.out.println(address);
        if (address == null) {
                throw new ResourceNotFoundException("Client adress " + address + " not found!");
            } else {
                Cart cart = client.getCart();
                System.out.println(cart);
                if (cart == null){
                    throw new ResourceNotFoundException("Cart not found!");
                } else{
                    Request request = new Request(address, cart);                    
                    repository.save(request);
                    return request;
                }
            }
        }
    }
}
