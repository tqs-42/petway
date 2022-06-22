package com.specific.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.specific.exception.ConflictException;
import com.specific.exception.ResourceNotFoundException;
import com.specific.model.User;
import com.specific.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public Optional<String> getFullName(String email) throws ResourceNotFoundException {
        User user = repository.findByEmail(email);
        if (user == null)
            throw new ResourceNotFoundException("Not found");
        String userFullName = user.getFullname();
        if (userFullName == null) throw new ResourceNotFoundException("Not found");
        return Optional.of(userFullName);
    }
}
