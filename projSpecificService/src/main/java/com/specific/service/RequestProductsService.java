package com.specific.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.specific.model.RequestProducts;
import com.specific.repository.RequestProductsRepository;

@Service
public class RequestProductsService {
    @Autowired
    private RequestProductsRepository repository;

    public RequestProducts saveRequestProducts(RequestProducts requestProducts) {
        return repository.save(requestProducts);
    }

    public List<RequestProducts> saveRequestsProducts(List<RequestProducts> requestsProducts) {
        return repository.saveAll(requestsProducts);
    }

    public List<RequestProducts> getRequestsProducts() {
        return repository.findAll();
    }

    public RequestProducts getRequestProductsById(long id) {
        return repository.findById(id).orElse(null);
    }

    public String deleteRequestProducts(long id) {
        repository.deleteById(id);
        return "requestProducts removed !! " + id;
    }

    public RequestProducts updateRequestProducts(RequestProducts requestProducts) {
        RequestProducts existingRequestProducts = repository.findById(requestProducts.getId()).orElse(null);
        existingRequestProducts.setAmount(requestProducts.getAmount());
        existingRequestProducts.setCart(requestProducts.getCart());
        existingRequestProducts.setProduct(requestProducts.getProduct());
        return repository.save(existingRequestProducts);
    }
}
