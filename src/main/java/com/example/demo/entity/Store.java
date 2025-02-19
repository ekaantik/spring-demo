package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import com.example.demo.constants.ServiceType;
import com.example.demo.entity.base.BaseUuidEntity;
import com.example.demo.security.entity.User;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "store")
public class Store extends BaseUuidEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 8592895910447049554L;

    @Column(name = "name")
    @Size(max = 255, message = "Name must be at most 255 characters.")
    private String name;

    @Column(name = "address")
    @Size(max = 255, message = "Address must be at most 255 characters.")
    private String address;

    @Column(name = "service_type")
    @NotNull
    private ServiceType serviceType;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_user_id")
    private User vendorUser;

    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Shift> shiftList;

}
