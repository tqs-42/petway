package com.engine.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.engine.app.exception.ConflictException;
import com.engine.app.exception.ResourceNotFoundException;
import com.engine.app.repository.StoreRepository;

import java.util.List;
import com.engine.app.model.Store;


@Service
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;

    public void addStore(String name, String address) throws ConflictException {
        if (storeRepository.findByName(name) != null) {
            throw new ConflictException("Store " + name + " already exists");
        } else {
            storeRepository.save(new Store(name, address));
        }
    }

    public List<Store> getAllStores() throws ResourceNotFoundException{
            return storeRepository.findAll();
    }
     
}
