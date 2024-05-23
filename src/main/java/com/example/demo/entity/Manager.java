package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import lombok.NonNull;

import com.example.demo.entity.base.BaseUuidEntity;
import com.example.demo.security.entity.User;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "manager_mapping")
public class Manager extends BaseUuidEntity {

    @ManyToOne
    @JsonBackReference
    @ToString.Exclude
    @JoinColumn(name = "vendor_id")
    private User vendorUser;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    @NonNull
    private User managerUser;
}