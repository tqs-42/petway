package com.specific.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.specific.model.Product;
import com.specific.repository.ProductRepository;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;

    public Product saveProduct(Product product) {
        return repository.save(product);
    }

    public List<Product> saveProducts(List<Product> products) {
        return repository.saveAll(products);
    }

    public List<Product> getProducts() {
        return repository.findAll();
    }

    public Product getProductById(long id) {
        return repository.findById(id).orElse(null);
    }

    public String deleteProduct(long id) {
        repository.deleteById(id);
        return "product removed !! " + id;
    }

    public Product updateProduct(Product product) {
        Product existingProduct = repository.findById(product.getId()).orElse(null);
        existingProduct.setDescription(product.getDescription());
        existingProduct.setName(product.getName());
        existingProduct.setImage(product.getImage());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setStore(product.getStore());
        existingProduct.setCategories(product.getCategories());
        existingProduct.setRequests(product.getRequests());
        existingProduct.setStock(product.getStock());
        return repository.save(existingProduct);
    }
}
