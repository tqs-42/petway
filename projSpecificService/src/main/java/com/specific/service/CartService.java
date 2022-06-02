package com.specific.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.specific.model.Cart;
import com.specific.repository.CartRepository;

@Service
public class CartService {
    @Autowired
    private CartRepository repository;

    public Cart saveCart(Cart cart) {
        return repository.save(cart);
    }

    public List<Cart> saveCarts(List<Cart> carts) {
        return repository.saveAll(carts);
    }

    public List<Cart> getCarts() {
        return repository.findAll();
    }

    public Cart getCartById(long id) {
        return repository.findById(id).orElse(null);
    }

    public String deleteCart(long id) {
        repository.deleteById(id);
        return "cart removed !! " + id;
    }

    public Cart updateCart(Cart cart) {
        Cart existingCart = repository.findById(cart.getId()).orElse(null);
        existingCart.setClient(cart.getClient());
        existingCart.setProducts(cart.getProducts());
        existingCart.setRequest(cart.getRequest());
        return repository.save(existingCart);
    }
}
