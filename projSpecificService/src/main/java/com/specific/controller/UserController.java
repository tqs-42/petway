package com.specific.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import com.specific.exception.ConflictException;
import com.specific.model.User;
import com.specific.service.UserService;
import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody Map<String, String> data) throws Exception {
        try {
            return ResponseEntity.ok(userService.loginUser(data.get("email"), data.get("password")));
        } catch (ConflictException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/userByEmail/{email}")
    public ResponseEntity<User> findUserByEmail(@Valid @PathVariable String email) {
        try {
            User user = userService.getUserByEmail(email);
            if (user == null) {
                return ResponseEntity.badRequest().body(null);
            }
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
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

    @PutMapping("/updateUser")
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @DeleteMapping("/deleteUser/{email}")
    public String deleteUser(@PathVariable String email) {
        return userService.deleteUser(email);
    }
}
