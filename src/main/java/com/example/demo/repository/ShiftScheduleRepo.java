package com.example.demo.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.ShiftSchedule;

public interface ShiftScheduleRepo extends JpaRepository<ShiftSchedule, UUID> {

}
