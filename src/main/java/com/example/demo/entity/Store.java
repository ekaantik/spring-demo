package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import com.example.demo.entity.base.BaseUuidEntity;
import com.example.demo.security.entity.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "store_mapping")
public class Store extends BaseUuidEntity {

    @ManyToOne
    @JoinColumn(name = "vendor_user_id")
    private User vendorUser;
}
