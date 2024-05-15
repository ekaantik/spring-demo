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
@Table(name = "images")
public class Images extends BaseUuidEntity {

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(name = "path")
    private String path;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private ImageCategories category;

}
