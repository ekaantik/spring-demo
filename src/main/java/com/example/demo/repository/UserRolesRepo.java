package com.example.demo.repository;

import com.example.iot.entity.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRolesRepo extends JpaRepository<UserRoles, UUID> {

}
