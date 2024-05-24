package com.example.demo.entity;

import com.example.demo.entity.base.BaseUuidEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "shift")
public class Shift extends BaseUuidEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 480487557972577024L;

    @ManyToOne
    @JoinColumn(name = "store_details_id")
    @JsonBackReference
    @ToString.Exclude
    private Store store;

    @Column(name = "name")
    private String name;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;
}