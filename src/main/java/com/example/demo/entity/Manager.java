package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.Set;

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
    @NotNull
    private User managerUser;
}