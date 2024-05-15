package com.example.demo.repository;

import com.example.demo.entity.Images;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ImageRepo extends JpaRepository<Images, UUID> {

}
