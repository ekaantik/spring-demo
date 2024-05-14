package com.example.demo.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Store;

public interface StoreRepo extends JpaRepository<Store, UUID> {

}
