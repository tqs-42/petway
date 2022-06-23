package com.engine.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
<<<<<<< HEAD
        registry.addMapping("/**").allowedOrigins("http://localhost:6869","http://localhost:19006").allowedHeaders("*").allowedMethods("*"); 
=======
        registry.addMapping("/**").allowedOrigins("http://localhost:6869","http://localhost:6868", "http://0.0.0.0:6869").allowedHeaders("*").allowedMethods("*"); 
>>>>>>> 320026fcf8651a1b872487133b560c32ab5d9790
    }
    
}