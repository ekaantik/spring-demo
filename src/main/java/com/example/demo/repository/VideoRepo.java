package com.example.demo.repository;

import com.example.demo.entity.Videos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface VideoRepo extends JpaRepository<Videos, UUID> {

    Optional<Videos> findByPath(String path);
}
