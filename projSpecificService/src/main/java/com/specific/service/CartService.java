package com.specific.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.specific.repository.CartRepository;

@Service
public class CartService {
    @Autowired
    private CartRepository repository;
}
