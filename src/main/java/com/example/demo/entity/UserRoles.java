package com.example.demo.entity;

import com.example.demo.security.entity.Role;
import com.example.demo.security.entity.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.NonNull;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@ToString
@Data
@Table(name = "user_roles")
public class UserRoles {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_details_id")
    @NonNull
    private User user;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "roles_id")
    private Role role;

    public UserRoles(Role role) {
        this.role = role;
    }
}
