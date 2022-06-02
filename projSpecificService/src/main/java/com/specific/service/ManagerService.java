package com.specific.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.specific.model.Manager;
import com.specific.repository.ManagerRepository;

@Service
public class ManagerService {
    @Autowired
    private ManagerRepository repository;

    public Manager saveManager(Manager manager) {
        return repository.save(manager);
    }

    public List<Manager> saveManagers(List<Manager> managers) {
        return repository.saveAll(managers);
    }

    public List<Manager> getManagers() {
        return repository.findAll();
    }

    public Manager getManagerByEmail(String email) {
        return repository.findByEmail(email);
    }

    public String deleteManager(String email) {
        repository.deleteByEmail(email);
        return "manager removed !! " + email;
    }

    public Manager updateManager(Manager manager) {
        Manager existingManager = repository.findByEmail(manager.getEmail());
        existingManager.setPassword(manager.getPassword());
        existingManager.setFullname(manager.getFullname());
        existingManager.setStore(manager.getStore());
        return repository.save(existingManager);
    }
}
