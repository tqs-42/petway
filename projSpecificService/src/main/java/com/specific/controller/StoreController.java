package com.specific.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.specific.service.StoreService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/stores")
public class StoreController {
    @Autowired
    private StoreService service;
}
