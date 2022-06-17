package com.specific.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.specific.repository.StoreRepository;

@Service
public class StoreService {
    @Autowired
    private StoreRepository repository;
}
