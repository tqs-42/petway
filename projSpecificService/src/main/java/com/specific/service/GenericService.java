package com.specific.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.specific.exception.ConflictException;
import com.specific.exception.ResourceNotFoundException;
import com.specific.model.Cart;
import com.specific.model.Client;
import com.specific.model.Product;
import com.specific.model.RequestProducts;
import com.specific.model.Store;
import com.specific.repository.ClientRepository;

@Service
public class GenericService {

    @Autowired
    private ClientRepository clientRepository;

    public Map<String, String> makeOrder(String userEmail) throws ResourceNotFoundException {
        Map<String, String> data = new HashMap<>();
        Client client = clientRepository.findByEmail(userEmail);
        System.out.println(client);
        if (client == null) {
            throw new ResourceNotFoundException("Client " + userEmail + " not found!");
        } else {
            System.out.println("Vamos pedidinho ");
            String address = client.getAddress();
            System.out.println(address);
            data.put("address", address);
            if (address == null) {
                throw new ResourceNotFoundException("Client adress " + address + " not found!");
            } else {
                Cart cart = client.getCart();
                System.out.println(cart);
                if (cart == null) {
                    throw new ResourceNotFoundException("Cart not found!");
                } else {
                    Set<RequestProducts> requestsProducts = cart.getProducts();
                    if (requestsProducts == null) {
                        throw new ResourceNotFoundException("Not found");
                    } else {
                        for (RequestProducts requestProducts : requestsProducts) {
                            Product requestProducts_product = requestProducts.getProduct();
                            if (requestProducts_product == null) {
                                throw new ResourceNotFoundException("Not found");
                            } else {
                                String storeName = requestProducts_product.getStore().getName();
                                if (storeName == null) {
                                    throw new ResourceNotFoundException("Not found");
                                } else {
                                    data.put("store", storeName);
                                    return data;
                                }
                            }
                        }
                    }
                }
            }
        }
        return data;
    }
}
