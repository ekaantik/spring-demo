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
@Table(name = "manager_mapping")
public class Manager extends BaseUuidEntity {

    @ManyToOne
    @JoinColumn(name = "vender_id")
    private User vendorUser;

    // TODO : Mutiple Manger can be in different vedors?
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "manager_user_id")
    @NonNull
    private User managerUser;
}