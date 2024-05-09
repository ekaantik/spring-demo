package com.ekaantik.demo.repository;

import com.ekaantik.demo.entity.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ConfigurationRepository extends JpaRepository<Configuration, Long> {
}
