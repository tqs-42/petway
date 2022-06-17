package com.specific.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.specific.service.ManagerService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/manageres")
public class ManagerController {

    @Autowired
    private ManagerService service;
}
