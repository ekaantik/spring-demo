package com.ekaantik.demo.repository;

import com.ekaantik.demo.entity.Vender;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface VenderRepository extends JpaRepository<Vender, UUID> {

}
