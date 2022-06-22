package com.specific.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.specific.exception.ConflictException;
import com.specific.model.User;
import com.specific.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;
}
