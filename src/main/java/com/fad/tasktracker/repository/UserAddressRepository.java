package com.fad.tasktracker.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fad.tasktracker.entities.User;
import com.fad.tasktracker.entities.UserAddress;

public interface UserAddressRepository extends JpaRepository<UserAddress, UUID>{
    List<UserAddress> findByUser(User user);
}
