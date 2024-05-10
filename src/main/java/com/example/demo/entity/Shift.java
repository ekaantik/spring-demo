package com.example.demo.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "shift")
public class Shift {

    @Id
    @Column(name = "manager_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private String startTime; //ZonedDateTime
    private String endTime; //ZonedDateTime
}