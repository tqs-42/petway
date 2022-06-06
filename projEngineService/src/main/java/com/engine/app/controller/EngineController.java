package com.engine.app.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class EngineController {

    @GetMapping("/riders")
	public String riders() {

		return "Hello World";
	}

	@GetMapping("/teste")
	public String login() {
		return "Hello World";
	}

	@GetMapping("/oi")
	public String register() {
		return "Hello World - register";
	}
    
}
