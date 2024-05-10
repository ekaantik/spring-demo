package com.example.demo.repository;

import com.example.demo.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ManagerRepository extends JpaRepository<Manager, UUID> {

}