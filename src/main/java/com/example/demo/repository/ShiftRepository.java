package com.example.demo.repository;
import com.example.demo.entity.Shift;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ShiftRepository extends JpaRepository<Shift, UUID> {

}