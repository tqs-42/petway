package com.specific.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public User saveUser(User user) {
        return repository.save(user);
    }

    public List<User> saveUsers(List<User> users) {
        return repository.saveAll(users);
    }

    public List<User> getUsers() {
        return repository.findAll();
    }

    public User getUserByEmail(String email) {
        return repository.findByEmail(email);
    }

    public String deleteUser(String email) {
        repository.deleteByEmail(email);
        return "user removed !! " + email;
    }

    public User updateUser(User user) {
        User existingUser = repository.findByEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        existingUser.setFullname(user.getFullname());
        return repository.save(existingUser);
    }
}
