package com.specific.service;

import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.specific.model.Cart;
import com.specific.model.Client;
import com.specific.model.Product;
import com.specific.model.RequestProducts;
import com.specific.repository.CartRepository;
import com.specific.repository.ClientRepository;
import com.specific.repository.ProductRepository;
import com.specific.repository.RequestProductsRepository;

@Service
public class CartService {
    @Autowired
    private CartRepository repository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private RequestProductsRepository requestProductsRepository;

    @Autowired
    private ProductRepository productRepository;
     
    public RequestProducts getProductAmout(String email, Long productId){
        Client client = clientRepository.findByEmail(email);
        Cart cart = repository.findByClient(client);
        Set<RequestProducts> requestsProducts = cart.getProducts();
        for (RequestProducts requestProducts : requestsProducts) {
            if (requestProducts.getProduct().getId() == productId) {
                return requestProducts;
            }
        }
        return null;
    }

    public RequestProducts putProductAmout(String email, long productId, int amount){
        Client client = clientRepository.findByEmail(email);
        Cart cart = repository.findByClient(client);
        Set<RequestProducts> requestsProducts = cart.getProducts();
        for (RequestProducts requestProducts : requestsProducts) {
            if (requestProducts.getProduct().getId() == productId) {
                if (amount == 0) {
                    requestProductsRepository.deleteById(requestProducts.getId());
                    return null;
                } else {
                    requestProducts.setAmount(amount);
                    return requestProductsRepository.save(requestProducts);
                }
            }
        }

        if (amount != 0) {
            Product product = productRepository.findById(productId);
            return requestProductsRepository.save(new RequestProducts(amount, cart, product));
        }
        return null;
    }

    public Set<RequestProducts> getProducts(String email){
        Client client = clientRepository.findByEmail(email);
        Cart cart = repository.findByClient(client);
        return cart.getProducts();
    }
    
}
