package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import org.springframework.lang.NonNull;

import com.example.demo.entity.base.BaseUuidEntity;
import com.example.demo.security.entity.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "technician_mapping")
public class Technician extends BaseUuidEntity {

    @ManyToOne
    @JoinColumn(name = "managed_by_id")
    private User managedByUser;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "technician_user_id")
    @NonNull
    private User technicianUser;
}