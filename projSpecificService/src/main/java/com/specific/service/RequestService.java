package com.specific.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.specific.repository.RequestRepository;

@Service
public class RequestService {
    @Autowired
    private RequestRepository repository;
}
