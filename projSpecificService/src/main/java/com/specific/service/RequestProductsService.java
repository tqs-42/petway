package com.specific.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.specific.exception.ResourceNotFoundException;
import com.specific.model.Cart;
import com.specific.model.Client;
import com.specific.model.RequestProducts;
import com.specific.repository.ClientRepository;
import com.specific.repository.RequestProductsRepository;

@Service
public class RequestProductsService {
    @Autowired
    private RequestProductsRepository requestProductsRepository;

    @Autowired
    private ClientRepository clientRepository;

    public Boolean deleteRequestProducts(String email) throws ResourceNotFoundException {
        Client client = clientRepository.findByEmail(email);
        System.out.println(client);
        if (client == null) {
            throw new ResourceNotFoundException("Client " + email + " not found!");
        } else {
            Cart cart = client.getCart();
            System.out.println(cart);
            if (cart == null) {
                throw new ResourceNotFoundException("Cart not found!");
            } else {
                Set<RequestProducts> requestsProducts = cart.getProducts();
                if (requestsProducts == null) throw new ResourceNotFoundException("Not found");
                for (RequestProducts requestProducts : requestsProducts) {
                    Optional<RequestProducts> requesteProductsOp = requestProductsRepository.findById(requestProducts.getId());
                    if (requesteProductsOp.isPresent() ) {
                        requestProductsRepository.delete(requesteProductsOp.get());
                    }else {
                        throw new ResourceNotFoundException("NOT FOUND");
                    }
                }
                return true;
            }
        }
    }
}
