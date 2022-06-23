package com.specific.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.specific.exception.ConflictException;
import com.specific.exception.ResourceNotFoundException;
import com.specific.model.Request;
import com.specific.service.RequestService;

@RestController
<<<<<<< HEAD
@CrossOrigin(origins = {"http://192.168.160.234:4201", "http://localhost:4201"})

=======
@CrossOrigin(origins =  {"http://localhost:4201", "http://0.0.0.0:6868"})
>>>>>>> 320026fcf8651a1b872487133b560c32ab5d9790
@RequestMapping("/requestes")
public class RequestController {
    @Autowired
    private RequestService service;

    @PostMapping("/add")
    public ResponseEntity<Request> addRequest(@RequestBody Map<String, String> data) throws ResourceNotFoundException, ConflictException {
        System.out.println("------------------LOL------------------------------");
        Request request;
        try {
            request = service.addRequest(data.get("userEmail"));
        } catch (ConflictException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(request);
    }
}
