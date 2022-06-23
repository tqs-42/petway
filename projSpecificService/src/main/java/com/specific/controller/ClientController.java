package com.specific.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.specific.service.ClientService;

@RestController
@CrossOrigin(origins = {"http://localhost:4201", "http://0.0.0.0:6868" })
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService service;
}