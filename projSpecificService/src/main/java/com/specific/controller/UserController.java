package com.specific.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import com.specific.exception.ResourceNotFoundException;
import com.specific.service.UserService;
import javax.validation.Valid;

@RestController
<<<<<<< HEAD
@CrossOrigin(origins = {"http://192.168.160.234:4201", "http://localhost:4201"})

=======
@CrossOrigin(origins =  {"http://localhost:4201", "http://0.0.0.0:6868"})
>>>>>>> 320026fcf8651a1b872487133b560c32ab5d9790
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/user/{email}/fullname")
    public Map<String, String> getFullName(@Valid @PathVariable String email) throws ResourceNotFoundException {
        String userFullName = service.getFullName(email)
                .orElseThrow(() -> new ResourceNotFoundException("managerEmail " + email + ", or store NOT FOUND."));
        Map<String, String> data = new HashMap<>();
        data.put("fullname", userFullName);
        return data;
    }
}
