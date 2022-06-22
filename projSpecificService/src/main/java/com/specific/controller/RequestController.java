package com.specific.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.specific.service.RequestService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/requestes")
public class RequestController {
    @Autowired
    private RequestService service;
}
