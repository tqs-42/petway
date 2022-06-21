package com.specific.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.specific.exception.ConflictException;
import com.specific.model.Store;
import com.specific.repository.StoreRepository;

@Service
public class StoreService {

    @Autowired
    private StoreRepository repository;

    public Store saveStore(Store store) throws ConflictException {
        if (repository.findByName(store.getName()) == null) {
            repository.saveAndFlush(store);
            return store;
        } else {
            throw new ConflictException("Store already exists");
        }
    }
    
}
