package com.example.demo.repository;

import com.example.iot.entity.CustomerDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerDetailsRepo extends JpaRepository<CustomerDetails, UUID> {

    Optional<CustomerDetails> findByCompanyEmail(String email);

    Optional<CustomerDetails> findByCustomerId(String customerId);

    Optional<CustomerDetails> findByAdminEmail(String email);
}
