package com.engine.app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EngineController {

    @RequestMapping({ "/hello" })
	public String firstPage() {
		return "Hello World";
	}
    
}
