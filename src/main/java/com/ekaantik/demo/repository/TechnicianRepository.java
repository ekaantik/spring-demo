package com.ekaantik.demo.repository;

import com.ekaantik.demo.entity.Technician;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TechnicianRepository extends JpaRepository<Technician, UUID> {

}
