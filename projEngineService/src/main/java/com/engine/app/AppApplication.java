package com.engine.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.engine.app.service.AdminService;

@SpringBootApplication
public class AppApplication {

	@Autowired
	private AdminService adminService;

	public static void main(String[] args) {
		
		SpringApplication.run(AppApplication.class, args);

	}

	public void run(String... args) throws Exception {
		adminService.registerAdmin("admin@ua.pt","admin123","Rua da Pega","Bernardo Farias");
	}
}
