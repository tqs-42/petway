package com.engine.app.service;

import org.apache.tomcat.util.file.ConfigurationSource.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.engine.app.exception.ConflictException;
import com.engine.app.exception.ResourceNotFoundException;
import com.engine.app.repository.StoreRepository;

import java.util.List;
import java.util.Optional;

import com.engine.app.model.Store;


@Service
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;

    public Store addStore(String name, String address) throws ConflictException {
        if (storeRepository.findByName(name) != null) {
            throw new ConflictException("Store " + name + " already exists");
        } else {
            Store store = new Store(name, address);
            storeRepository.save(store);
            return store;
        }
    }

    public Boolean deleteStore(Long id) throws ResourceNotFoundException {
        Optional<Store> optional = storeRepository.findById(id);
        if (optional.isPresent()) {
            storeRepository.delete(optional.get());
            return true;
        } else {
            throw new ResourceNotFoundException("Store doesn't exist");
        }
    }

    public Store getStore(String name) throws ResourceNotFoundException {
        Store store = storeRepository.findByName(name);
        if (store != null) return store;
        else throw new ResourceNotFoundException("Store doesn't exist");
    }

    public List<Store> getAllStores() throws ResourceNotFoundException{
            return storeRepository.findAll();
    }
     
}
