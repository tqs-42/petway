package com.specific.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.specific.repository.ManagerRepository;

@Service
public class ManagerService {
    @Autowired
    private ManagerRepository repository;
}
