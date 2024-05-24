package com.example.demo.entity;

import com.example.demo.entity.base.BaseUuidEntity;
import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "shift_schedule")
public class ShiftSchedule extends BaseUuidEntity {

    @Column(name = "store_id")
    private UUID storeId;

    @Column(name = "shift_id" , unique = true)
    private UUID shiftId;

    @Column(name = "name")
    private String name;

    @Column(name = "start_time")
    private ZonedDateTime startTime;

    @Column(name = "end_time")
    private ZonedDateTime endTime;

    @Column(name = "date")
    private LocalDate date;
}