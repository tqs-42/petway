package com.specific.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.specific.model.Category;
import com.specific.model.Product;
import com.specific.model.Store;
import com.specific.repository.CategoryRepository;
import com.specific.repository.ProductRepository;
import com.specific.repository.StoreRepository;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public Product saveProduct(Map<String, String> data) {
        String name = data.get("name");
        String description = data.get("description");
        String image = data.get("image");
        Double price = Double.parseDouble(data.get("price"));
        int stock = Integer.parseInt(data.get("stock"));
        Store store = storeRepository.findById(Long.parseLong(data.get("store")));
        Category category = categoryRepository.findById(Long.parseLong(data.get("category")));
        
        Product product = new Product(name, description, image, price, stock, category, store);
        return productRepository.saveAndFlush(product);
    }

    public Store getStoreIdByManager(String manager) {
        return storeRepository.getStoreByManagers(manager);
    }

    public List<Product> saveProducts(List<Product> products) {
        return productRepository.saveAll(products);
    }

    public List<Product> getProducts() {
        System.out.println(productRepository.findAll());
        return productRepository.findAll();
    }

    public Product getProductById(long id) {
        return productRepository.findById(id).orElse(null);
    }

    public String deleteProduct(long id) {
        productRepository.deleteById(id);
        return "product removed !! " + id;
    }

    public Product updateProduct(Product product) {
        Product existingProduct = productRepository.findById(product.getId()).orElse(null);
        existingProduct.setDescription(product.getDescription());
        existingProduct.setName(product.getName());
        existingProduct.setImage(product.getImage());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setStore(product.getStore());
        existingProduct.setCategories(product.getCategories());
        existingProduct.setRequests(product.getRequests());
        return productRepository.save(existingProduct);
    }
}
