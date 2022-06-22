package com.engine.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.engine.app.exception.ConflictException;
import com.engine.app.model.Admin;
import com.engine.app.repository.AdminRepository;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Admin registerAdmin(String email,String password,String address, String fullname) throws ConflictException {
        if (adminRepository.findByEmail(email) != null) {
            throw new ConflictException("Admin " + email + " already registered");
        } else {
            Admin admin = new Admin(email, address, fullname, password);
            admin.setPassword(passwordEncoder.encode(password));
            adminRepository.save(admin);
        }
        return null;
    }
}