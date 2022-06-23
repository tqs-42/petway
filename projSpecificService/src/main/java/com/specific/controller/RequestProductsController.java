package com.specific.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.specific.service.RequestProductsService;

@RestController
@CrossOrigin(origins = "http://localhost:4201")
@RequestMapping("/requestesProducts")
public class RequestProductsController {
    @Autowired
    private RequestProductsService service;
}
