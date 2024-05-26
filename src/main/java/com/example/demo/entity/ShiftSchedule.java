package com.example.demo.entity;

import com.example.demo.entity.base.BaseUuidEntity;
import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "shift_schedule" , uniqueConstraints = { @UniqueConstraint(columnNames = { "store_id", "name" }) })
public class ShiftSchedule extends BaseUuidEntity {

    @Column(name = "store_id")
    @NotNull
    private UUID storeId;

    @Column(name = "shift_id" , unique = true)
    @NotNull
    private UUID shiftId;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "start_time")
    @NotNull
    private ZonedDateTime startTime;

    @Column(name = "end_time")
    @NotNull
    private ZonedDateTime endTime;

    @Column(name = "date")
    @NotNull
    private LocalDate date;
}