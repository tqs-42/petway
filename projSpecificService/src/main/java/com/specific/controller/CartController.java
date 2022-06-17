package com.specific.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.specific.service.CartService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/carts")
public class CartController {
    @Autowired
    private CartService service;

}
