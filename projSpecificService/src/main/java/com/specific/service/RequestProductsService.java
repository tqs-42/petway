package com.specific.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.specific.repository.RequestProductsRepository;

@Service
public class RequestProductsService {
    @Autowired
    private RequestProductsRepository repository;
}
