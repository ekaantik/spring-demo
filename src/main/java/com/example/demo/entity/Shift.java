package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import com.example.demo.constants.ShiftType;
import com.example.demo.entity.base.BaseUuidEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "shift")
public class Shift extends BaseUuidEntity {

    @Column(name = "shift_type")
    private ShiftType shiftType;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;
}