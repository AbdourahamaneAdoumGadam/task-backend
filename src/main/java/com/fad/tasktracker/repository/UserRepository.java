package com.fad.tasktracker.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fad.tasktracker.entities.User;
import com.fad.tasktracker.entities.enums.Role;

public interface UserRepository  extends JpaRepository<User, UUID>{
    Optional<User> findByEmail(String email);
    List<User> findByRole(Role role);
}