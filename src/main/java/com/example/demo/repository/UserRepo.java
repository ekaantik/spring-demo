package com.example.demo.repository;

import com.example.demo.entity.CustomerDetails;
import com.example.demo.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepo extends JpaRepository<User, UUID> {

    Optional<User> findById(UUID id);

    Optional<User> findByEmailAndPassword(String email, String password);

    Optional<User> findByEmail(String email);
    List<User> findByCustomerDetails(CustomerDetails customerDetails);
}
