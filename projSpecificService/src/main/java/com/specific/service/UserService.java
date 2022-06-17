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

    public User loginUser(String email, String password) throws ConflictException {
        User user = repository.findByEmail(email);
        if (user == null) {
            throw new ConflictException("User not found");
        }

        if (!password.equals(user.getPassword())) {
            throw new ConflictException("Wrong password");
        }

        return user;
    }

    public User getUserByEmail(String email) {
        return repository.findByEmail(email);
    }
}
