package com.specific.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.specific.model.Cart;
import com.specific.service.CartService;

@RestController
@RequestMapping("/api/specific")
public class CartController {
    @Autowired
    private CartService service;

    @PostMapping("/addCart")
    public Cart addCart(@RequestBody Cart cart) {
        return service.saveCart(cart);
    }

    @PostMapping("/addCarts")
    public List<Cart> addCarts(@RequestBody List<Cart> carts) {
        return service.saveCarts(carts);
    }

    @GetMapping("/carts")
    public List<Cart> findAllCarts() {
        return service.getCarts();
    }

    @GetMapping("/cartById/{id}")
    public Cart findCartById(@PathVariable int id) {
        return service.getCartById(id);
    }

    @PutMapping("/updateCart")
    public Cart updateCart(@RequestBody Cart cart) {
        return service.updateCart(cart);
    }

    @DeleteMapping("/deleteCart/{id}")
    public String deleteCart(@PathVariable int id) {
        return service.deleteCart(id);
    }
}
