package com.specific.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     
    public Optional<RequestProducts> getProductAmout(String email, Long productId){
        Client client = clientRepository.findByEmail(email);
        if (client != null) {
            Cart cart = repository.findByClient(client);
            if (cart != null) {
                Set<RequestProducts> requestsProducts = cart.getProducts();
                for (RequestProducts requestProducts : requestsProducts) {
                    if (requestProducts.getProduct().getId() == productId) {
                        return Optional.of(requestProducts);
                    }
                }                  
            }
        }
        //retornar uma exececao resourcenotfoundexception, nao encontrou client, ou cart, ou o produto no carrinho
        return Optional.empty();
    }

    public  Optional<RequestProducts> putProductAmout(String email, long productId, int amount) {
        String storeName = "";
        System.out.println("TESTE");
        Client client = clientRepository.findByEmail(email);
        System.out.println(client);
        if (client != null) {
            Cart cart = repository.findByClient(client);
            System.out.println(cart);
            if (cart != null) {
                Set<RequestProducts> requestsProducts = cart.getProducts();
                System.out.println("FOR");
                for (RequestProducts requestProducts : requestsProducts) {
                    System.out.println("ENTROU FOR");
                    System.out.println(requestProducts);
                    if (requestProducts.getProduct().getStore() != null) {
                        storeName = requestProducts.getProduct().getStore().getName();
                        if (requestProducts.getProduct().getId() == productId) {
                            System.out.println("entrou no if");
                            if (amount == 0) {
                                requestProductsRepository.deleteById(requestProducts.getId());
                                requestProducts.setAmount(0);
                                System.out.println("DELETEEEEEEEE");
                                return Optional.of(requestProducts);
                            } else {
                                System.out.println("EDITAR ");
                                requestProducts.setAmount(amount);
                                System.out.println(requestProducts);
                                RequestProducts savedr = requestProductsRepository.save(requestProducts);
                                System.out.println("dps de salvar");
                                System.out.println(savedr);
                                return Optional.of(savedr);
                            }
                        }
                    }else{
                        //retornar uma exececao resourcenotfoundexception, nao encontrou store
                        return Optional.empty();
                    }
                }
        
                System.out.println("TENTAR POST");
                Product product = productRepository.findById(productId);
                if (product != null) {
                    if (amount != 0) {
                        if (!storeName.equals("") && !storeName.equals(product.getStore().getName())) {
                            //queria retornar uma exececao que diz que todos os produtos que estao no carrinho têm de ser da mesma loja
                            return Optional.empty();
                        }
                        return Optional.of(requestProductsRepository.save(new RequestProducts(amount, cart, product)));
                    }
                    return Optional.of(new RequestProducts(0, cart, product));
                }
            }
        }
        //retornar uma exececao resourcenotfoundexception, nao encontrou client, ou cart, ou product
        return Optional.empty();
    }

    public Optional<List<RequestProducts>> getProducts(String email) throws ResourceNotFoundException{
        /* 
        Client client = clientRepository.findByEmail(email);
        if (client != null) {
            Cart cart = repository.findByClient(client);
            if (cart != null) {
                Set<RequestProducts> requestsProducts = cart.getProducts();
                if (requestsProducts != null) {
                    return Optional.of(requestsProducts.stream().collect(Collectors.toList()));       
                }
            }
        }
        return Optional.empty();
        */

        //retornar uma exececao resourcenotfoundexception, nao encontrou client, ou cart, ou os produtos de um carrinho
        Client client = clientRepository.findByEmail(email);
        if (client == null) throw new ResourceNotFoundException("Not found");
        Cart cart = repository.findByClient(client);
        if (cart == null) throw new ResourceNotFoundException("Not found");
        Set<RequestProducts> requestsProducts = cart.getProducts();
        if (requestsProducts == null) throw new ResourceNotFoundException("Not found");
        return Optional.of(requestsProducts.stream().collect(Collectors.toList()));    
    }
    
}
