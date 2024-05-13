package com.example.demo.entity;

import com.example.demo.constants.ImageCategories;
import com.example.demo.entity.base.BaseUuidEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "store")
public class Images extends BaseUuidEntity {

    @ManyToOne
    @Column(name = "store_id")
    private UUID storeId;

    @Column(name = "path")
    private String path;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private ImageCategories category;

}
