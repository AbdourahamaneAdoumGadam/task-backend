package com.fad.tasktracker.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fad.tasktracker.entities.User;
import com.fad.tasktracker.entities.UserAddress;
import com.fad.tasktracker.repository.UserAddressRepository;

@Service
public class UserAddressService {

    private final UserAddressRepository userAddressRepository;

    @Autowired
    public UserAddressService(UserAddressRepository userAddressRepository) {
        this.userAddressRepository = userAddressRepository;
    }

    public Optional<UserAddress> findById(UUID id) {
        return userAddressRepository.findById(id);
    }

    public List<UserAddress> findByUser(User user) {
        return userAddressRepository.findByUser(user);
    }

    public UserAddress save(UserAddress userAddress) {
        return userAddressRepository.save(userAddress);
    }

    public void deleteById(UUID id) {
        userAddressRepository.deleteById(id);
    }
}