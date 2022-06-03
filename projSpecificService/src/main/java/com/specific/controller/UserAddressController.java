package com.specific.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.specific.model.UserAddress;
import com.specific.service.UserAddressService;

@RestController
@RequestMapping("/api/specific")
public class UserAddressController {
    @Autowired
    private UserAddressService service;

    @PostMapping("/addUserAddress")
    public UserAddress addUserAddress(@RequestBody UserAddress userAddress) {
        return service.saveUserAddress(userAddress);
    }

    @PostMapping("/addUserAddresses")
    public List<UserAddress> addUserAddresses(@RequestBody List<UserAddress> userAddresses) {
        return service.saveUserAddresses(userAddresses);
    }

    @GetMapping("/userAddresses")
    public List<UserAddress> findAllUserAddresses() {
        return service.getUserAddresses();
    }

    @GetMapping("/userAddressById/{id}")
    public UserAddress findUserAddressById(@PathVariable int id) {
        return service.getUserAddressById(id);
    }

    @PutMapping("/updateUserAddress")
    public UserAddress updateUserAddress(@RequestBody UserAddress userAddress) {
        return service.updateUserAddress(userAddress);
    }

    @DeleteMapping("/deleteUserAddress/{id}")
    public String deleteUserAddress(@PathVariable int id) {
        return service.deleteUserAddress(id);
    }
}
