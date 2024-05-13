package com.example.demo.repository;

import com.example.demo.entity.Shift;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ShiftRepo extends JpaRepository<Shift, UUID> {

    Optional<Shift> findByStoreId(UUID id);


}