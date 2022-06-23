package com.specific.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.Map;
import com.specific.model.Client;
import com.specific.service.ClientService;

@RestController
@CrossOrigin(origins = "http://localhost:4201")
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService service;
}