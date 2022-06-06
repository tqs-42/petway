package com.specific.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

import com.engine.app.exception.ConflictException;
import com.specific.model.User;
import com.specific.service.ClientService;
import com.specific.service.UserService;

import com.google.gson.Gson;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    private static final Gson gson = new Gson();

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody Map<String, String> data) throws Exception {
        try {
            userService.loginUser(data.get("email"), data.get("password"));
        } catch (ConflictException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(gson.toJson("This is a String"));
    }

    @PostMapping("/addUser")
    public User addUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @PostMapping("/addUsers")
    public List<User> addUsers(@RequestBody List<User> users) {
        return userService.saveUsers(users);
    }

    @GetMapping("/users")
    public List<User> findAllUsers() {
        return userService.getUsers();
    }

    @GetMapping("/userByEmail/{email}")
    public User findUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    @PutMapping("/updateUser")
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @DeleteMapping("/deleteUser/{email}")
    public String deleteUser(@PathVariable String email) {
        return userService.deleteUser(email);
    }
}
