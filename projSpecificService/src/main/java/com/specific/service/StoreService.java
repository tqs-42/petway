package com.specific.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.specific.model.Store;
import com.specific.repository.StoreRepository;

@Service
public class StoreService {
    @Autowired
    private StoreRepository repository;

    public Store saveStore(Store store) {
        return repository.save(store);
    }

    public List<Store> saveStores(List<Store> stores) {
        return repository.saveAll(stores);
    }

    public List<Store> getStores() {
        return repository.findAll();
    }

    public Store getStoreById(long id) {
        return repository.findById(id);
    }

    public String deleteStore(long id) {
        repository.deleteById(id);
        return "store removed !! " + id;
    }

    public Store updateStore(Store store) {
        Store existingStore = repository.findById(store.getId());
        existingStore.setAddress(store.getAddress());
        existingStore.setName(store.getName());
        existingStore.setActive(store.getActive());
        existingStore.setManagers(store.getManagers());
        existingStore.setProducts(store.getProducts());
        return repository.save(existingStore);
    }
}
