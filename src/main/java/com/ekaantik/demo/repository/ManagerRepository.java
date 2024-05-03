package com.ekaantik.demo.repository;

import com.ekaantik.demo.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ManagerRepository extends JpaRepository<Manager, UUID> {

}
