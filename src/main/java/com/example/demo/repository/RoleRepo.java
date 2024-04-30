package com.example.demo.repository;

import com.example.demo.entity.CustomerDetails;
import com.example.demo.security.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepo extends JpaRepository<Role, UUID> {


    List<Role> findByCustomerDetailsId(UUID id);

    void deleteRoleById(@Param("id") UUID id);

    Optional<Role> findByNameAndCustomerDetails(String roleName, CustomerDetails customerDetails);
}
