package com.specific.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.specific.service.StoreService;

@RestController
@CrossOrigin(origins =  {"http://localhost:4201", "http://0.0.0.0:6868"})
@RequestMapping("/stores")
public class StoreController {
    @Autowired
    private StoreService service;
}
