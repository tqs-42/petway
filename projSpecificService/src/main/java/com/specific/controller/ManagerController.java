package com.specific.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.specific.model.Manager;
import com.specific.service.ManagerService;

@RestController
@RequestMapping("/api/specific")
public class ManagerController {

    @Autowired
    private ManagerService service;

    @PostMapping("/addManager")
    public Manager addManager(@RequestBody Manager manager) {
        return service.saveManager(manager);
    }

    @PostMapping("/addManagers")
    public List<Manager> addManagers(@RequestBody List<Manager> managers) {
        return service.saveManagers(managers);
    }

    @GetMapping("/managers")
    public List<Manager> findAllManagers() {
        return service.getManagers();
    }

    @GetMapping("/managerByEmail/{email}")
    public Manager findManagerByEmail(@PathVariable String email) {
        return service.getManagerByEmail(email);
    }

    @PutMapping("/updateManager")
    public Manager updateManager(@RequestBody Manager manager) {
        return service.updateManager(manager);
    }

    @DeleteMapping("/deleteManager/{email}")
    public String deleteManager(@PathVariable String email) {
        return service.deleteManager(email);
    }   
}
