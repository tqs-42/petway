package com.specific.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.specific.service.RequestProductsService;

@RestController
@CrossOrigin(origins = {"http://192.168.160.234:4201", "http://localhost:4201"})

@RequestMapping("/requestesProducts")
public class RequestProductsController {
    @Autowired
    private RequestProductsService service;
}
