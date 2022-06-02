package com.specific.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.specific.model.UserAddress;
import com.specific.repository.UserAddressRepository;

@Service
public class UserAddressService {
    @Autowired
    private UserAddressRepository repository;

    public UserAddress saveUserAddress(UserAddress userAddress) {
        return repository.save(userAddress);
    }

    public List<UserAddress> saveUserAddresses(List<UserAddress> userAddresses) {
        return repository.saveAll(userAddresses);
    }

    public List<UserAddress> getUserAddresses() {
        return repository.findAll();
    }

    public UserAddress getUserAddressById(long id) {
        return repository.findById(id).orElse(null);
    }

    public String deleteUserAddress(long id) {
        repository.deleteById(id);
        return "userAddress removed !! " + id;
    }

    public UserAddress updateUserAddress(UserAddress userAddress) {
        UserAddress existingUserAddress = repository.findById(userAddress.getId()).orElse(null);
        existingUserAddress.setAddress(userAddress.getAddress());
        existingUserAddress.setClient(userAddress.getClient());
        return repository.save(existingUserAddress);
    }
}
