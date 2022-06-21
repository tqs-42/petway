package com.specific.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.specific.exception.ConflictException;
import com.specific.exception.ResourceNotFoundException;
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

    public Optional<RequestProducts> getProductAmout(String email, Long productId) throws ResourceNotFoundException {
        Client client = clientRepository.findByEmail(email);
        if (client == null) throw new ResourceNotFoundException("Not found");
        Cart cart = repository.findByClient(client);
        if (cart == null) throw new ResourceNotFoundException("Not found");
        Set<RequestProducts> requestsProducts = cart.getProducts();
        if (requestsProducts == null) throw new ResourceNotFoundException("Not found");
        for (RequestProducts requestProducts : requestsProducts) {
            Product requestProducts_product = requestProducts.getProduct();
            if (requestProducts_product == null) throw new ResourceNotFoundException("Not found");
            if (requestProducts_product.getId() == productId) {
                return Optional.of(requestProducts);
            }
        }
        throw new ResourceNotFoundException("Not found");
    }

    public Optional<RequestProducts> putProductAmout(String email, long productId, int amount) throws ResourceNotFoundException, ConflictException {
        String storeName = "";
        
        Client client = clientRepository.findByEmail(email);
        if (client == null) throw new ResourceNotFoundException("Not found");
        
        Cart cart = repository.findByClient(client);
        if (cart == null) throw new ResourceNotFoundException("Not found");
        
        Set<RequestProducts> requestsProducts = cart.getProducts();
        if (requestsProducts == null) throw new ResourceNotFoundException("Not found");
        
        for (RequestProducts requestProducts : requestsProducts) {
            Product requestProducts_product = requestProducts.getProduct();
            if (requestProducts_product == null) throw new ResourceNotFoundException("Not found");

            storeName = requestProducts_product.getStore().getName();
            if (storeName == null) throw new ResourceNotFoundException("Not found");
            
            if (requestProducts_product.getId() == productId) {
                if (amount == 0) {
                    requestProductsRepository.deleteById(requestProducts.getId());
                    requestProducts.setAmount(0);
                    return Optional.of(requestProducts);
                } else {
                    requestProducts.setAmount(amount);
                    RequestProducts savedr = requestProductsRepository.save(requestProducts);
                    return Optional.of(savedr);
                }
            }
        }

        Product product = productRepository.findById(productId);
        if (product == null) throw new ResourceNotFoundException("Not found");

        if (amount != 0) {
            String product_storeName = product.getStore().getName();
            if (product_storeName == null) throw new ResourceNotFoundException("Not found");
            if (!storeName.equals("") && !storeName.equals(product_storeName)) throw new ConflictException("[ERROR] The products in the cart must be from the same store!");
            return Optional.of(requestProductsRepository.save(new RequestProducts(amount, cart, product)));
        }
        return Optional.of(new RequestProducts(0, cart, product));
    }

    public Optional<List<RequestProducts>> getProducts(String email) throws ResourceNotFoundException {
        Client client = clientRepository.findByEmail(email);
        if (client == null)
            throw new ResourceNotFoundException("Not found");
        Cart cart = repository.findByClient(client);
        if (cart == null)
            throw new ResourceNotFoundException("Not found");
        Set<RequestProducts> requestsProducts = cart.getProducts();
        if (requestsProducts == null)
            throw new ResourceNotFoundException("Not found");
        return Optional.of(requestsProducts.stream().collect(Collectors.toList()));
    }

}
