package com.example.demo.security.entity;

import com.example.iot.entity.AssetDetails;
import com.example.iot.entity.CustomerDetails;
import com.example.iot.entity.base.BaseUuidEntity;
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
@Table(name = "roles", uniqueConstraints = @UniqueConstraint(columnNames = { "name",
        "customer_details_id" }))
public class Role extends BaseUuidEntity implements Serializable {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    @NonNull
    private String description;

    @ManyToOne
    @JoinColumn(name = "customer_details_id")
//    @JsonBackReference
    @ToString.Exclude
    private CustomerDetails customerDetails;

    @ManyToMany
    @JoinTable(name = "role_asset_mapping", joinColumns = @JoinColumn(name = "role_details_id"), inverseJoinColumns = @JoinColumn(name = "asset_details_id"))
    private Set<AssetDetails> assetDetails;

    public Role(String name) {
        this.name = name;
    }
}
