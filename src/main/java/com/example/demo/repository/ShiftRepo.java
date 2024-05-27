package com.example.demo.repository;

import com.example.demo.entity.Shift;

import jakarta.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;
import java.util.UUID;

public interface ShiftRepo extends JpaRepository<Shift, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
//    @Lock(LockModeType.OPTIMISTIC)
    Optional<Shift> findByStoreId(UUID id);




}