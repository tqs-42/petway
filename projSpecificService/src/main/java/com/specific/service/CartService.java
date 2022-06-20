package com.specific.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
     
    public Optional<RequestProducts> getProductAmout(String email, Long productId){
        Client client = clientRepository.findByEmail(email);
        Cart cart = repository.findByClient(client);
        Set<RequestProducts> requestsProducts = cart.getProducts();
        for (RequestProducts requestProducts : requestsProducts) {
            if (requestProducts.getProduct().getId() == productId) {
                return Optional.of(requestProducts);
            }
        }
        return Optional.empty();
    }

    public  Optional<RequestProducts> putProductAmout(String email, long productId, int amount) {
        Client client = clientRepository.findByEmail(email);
        Cart cart = repository.findByClient(client);
        Set<RequestProducts> requestsProducts = cart.getProducts();
        for (RequestProducts requestProducts : requestsProducts) {
            if (requestProducts.getProduct().getId() == productId) {
                if (amount == 0) {
                    requestProductsRepository.deleteById(requestProducts.getId());
                    return Optional.of(new RequestProducts(0, cart, requestProducts.getProduct()));
                } else {
                    requestProducts.setAmount(amount);
                    return Optional.of(requestProductsRepository.save(requestProducts));
                }
            }
        }

        Product product = productRepository.findById(productId);
        if (amount != 0 && client != null && cart != null && product != null) {
            return Optional.of(requestProductsRepository.save(new RequestProducts(amount, cart, product)));
        }
        return Optional.empty();
    }

    public Optional<List<RequestProducts>> getProducts(String email){
        Client client = clientRepository.findByEmail(email);
        Cart cart = repository.findByClient(client);
        List<RequestProducts> requestsProducts = cart.getProducts().stream().collect(Collectors.toList());
        if (requestsProducts == null) {
            return Optional.empty();
        }
        return Optional.of(requestsProducts);
    }
    
}
