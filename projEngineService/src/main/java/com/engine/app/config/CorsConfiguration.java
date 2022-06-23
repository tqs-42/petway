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
        registry.addMapping("/**").allowedOrigins("http://localhost:6869").allowedHeaders("*").allowedMethods("*"); 
=======
        registry.addMapping("/**").allowedOrigins("http://localhost:6869","http://localhost:19006").allowedHeaders("*").allowedMethods("*"); 
>>>>>>> 82eb76d70af4d295c5a7121f1c7c658d366ab748
    }
    
}