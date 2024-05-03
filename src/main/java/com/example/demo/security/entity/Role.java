package com.example.demo.security.entity;


import com.example.demo.entity.base.BaseUuidEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "roles")
//, uniqueConstraints = @UniqueConstraint(columnNames = { "name", "customer_details_id"
public class Role extends BaseUuidEntity implements Serializable {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    @NonNull
    private String description;

    public Role(String name) {
        this.name = name;
    }
}
