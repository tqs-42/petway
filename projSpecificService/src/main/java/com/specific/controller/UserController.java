package com.specific.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.specific.model.User;
import com.specific.service.UserService;

@RestController
@RequestMapping("/api/specific")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/addUser")
    public User addUser(@RequestBody User user) {
        return service.saveUser(user);
    }

    @PostMapping("/addUsers")
    public List<User> addUsers(@RequestBody List<User> users) {
        return service.saveUsers(users);
    }

    @GetMapping("/users")
    public List<User> findAllUsers() {
        return service.getUsers();
    }

    @GetMapping("/userByEmail/{email}")
    public User findUserByEmail(@PathVariable String email) {
        return service.getUserByEmail(email);
    }

    @PutMapping("/updateUser")
    public User updateUser(@RequestBody User user) {
        return service.updateUser(user);
    }

    @DeleteMapping("/deleteUser/{email}")
    public String deleteUser(@PathVariable String email) {
        return service.deleteUser(email);
    }   
}
