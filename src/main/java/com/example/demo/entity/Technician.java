package com.example.demo.entity;

import com.example.demo.entity.base.BaseUuidEntity;
import com.example.demo.security.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "technician_mapping")
public class Technician extends BaseUuidEntity {

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "managed_by_id")
    private User managedByUser;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "technician_user_id")
    @NotNull
    private User technicianUser;
}