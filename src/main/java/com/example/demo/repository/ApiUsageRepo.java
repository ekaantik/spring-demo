package com.example.demo.repository;

import com.example.demo.entity.ApiUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ApiUsageRepo extends JpaRepository<ApiUsage, UUID> {

}
