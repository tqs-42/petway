package com.specific.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.specific.exception.ResourceNotFoundException;
import com.specific.exception.ConflictException;
import com.specific.model.Manager;
import com.specific.model.Store;
import com.specific.repository.ManagerRepository;

@Service
public class ManagerService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ManagerRepository managerRepository;

    public Manager saveManager(Manager manager) throws ConflictException {
        if (managerRepository.findByEmail(manager.getEmail()) == null) {
            manager.setPassword(passwordEncoder.encode(manager.getPassword()));
            managerRepository.saveAndFlush(manager);
            return manager;

        } else {
            throw new ConflictException("Manager already exists!");
        }
    }

    public Optional<Store> getStore(String email) throws ResourceNotFoundException {
        Manager manager = managerRepository.findByEmail(email);
        if (manager == null)
            throw new ResourceNotFoundException("Not found");
        Store store = manager.getStore();
        if (store == null)
            throw new ResourceNotFoundException("Not found");
        return Optional.of(store);
    }

}
