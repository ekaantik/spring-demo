package com.example.demo.repository;

import com.example.demo.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VendorRepository extends JpaRepository<Vendor, UUID> {

}