package com.specific.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.specific.service.ClientService;

@RestController
<<<<<<< HEAD
@CrossOrigin(origins = { "http://192.168.160.234:4201", "http://localhost:4201" })

=======
@CrossOrigin(origins = {"http://localhost:4201", "http://0.0.0.0:6868" })
>>>>>>> 320026fcf8651a1b872487133b560c32ab5d9790
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService service;
}