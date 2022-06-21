package com.specific.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.specific.model.Manager;
import com.specific.repository.ManagerRepository;

@Service
public class ManagerService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ManagerRepository repository;

    public Manager saveManager(Manager manager) throws Exception {
        if (repository.findByEmail(manager.getEmail()) == null) {
            manager.setPassword(passwordEncoder.encode(manager.getPassword()));
            repository.saveAndFlush(manager);
            return manager;

        } else {
            throw new Exception();
        }
    }

}
