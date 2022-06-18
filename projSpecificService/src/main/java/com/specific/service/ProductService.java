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
        System.out.println(name);
        System.out.println(description);
        System.out.println(image);
        System.out.println(price);
        System.out.println(stock);
        System.out.println(store);
        System.out.println(category);
        Product product = new Product(name, description, image, price, stock, category, store);
        System.out.println(product);
        return productRepository.saveAndFlush(product);
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }
}
