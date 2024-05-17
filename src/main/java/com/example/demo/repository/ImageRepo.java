package com.example.demo.repository;

import com.example.demo.entity.Images;
import com.example.demo.entity.Shift;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ImageRepo extends JpaRepository<Images, UUID> {
    Optional<Images> findByPath(String path);
}
