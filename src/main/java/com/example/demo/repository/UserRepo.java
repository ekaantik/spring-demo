package com.example.demo.repository;

import com.example.demo.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepo extends JpaRepository<User, UUID> {
    
    Optional<User> findByPhoneNumberAndPassword(String phoneNumber, String password);

    Optional<User> findByPhoneNumber(String phoneNumber);
}
