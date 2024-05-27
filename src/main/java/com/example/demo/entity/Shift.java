package com.example.demo.entity;

import com.example.demo.entity.base.BaseUuidEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "shift", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "store_details_id", "name" })
})
public class Shift extends BaseUuidEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 480487557972577024L;

    @ManyToOne
    @JoinColumn(name = "store_details_id")
    @JsonBackReference
    @ToString.Exclude
    private Store store;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "start_time")
    @NotNull
    private LocalTime startTime;

    @Column(name = "end_time")
    @NotNull
    private LocalTime endTime;
}