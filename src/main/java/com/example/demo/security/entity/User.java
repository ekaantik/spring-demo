package com.example.demo.security.entity;


import com.example.demo.constants.UserType;
import com.example.demo.entity.Manager;
import com.example.demo.entity.base.BaseUuidEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString
@SuperBuilder
@Entity
@Table(name = "user_details")
public class User extends BaseUuidEntity implements UserDetails, Serializable {

    @Column(name = "first_name")
    @NotNull
    @Size(max = 255, message = "First Name must be at most 255 characters.")
    private String firstName;

    @Column(name = "last_name")
    @NotNull
    @Size(max = 255, message = "Last Name must be at most 255 characters.")
    private String lastName;

    @Column
    @NotNull
    @Size(max = 255, message = "password Name must be at most 255 characters.")
    private String password;

    @Column(name = "phone_number", unique = true)
    @NotNull
    @Pattern(regexp = "(^$|[0-9]{10})")
    private String phoneNumber;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    @NotNull
    private UserType userType;

    @OneToMany(mappedBy = "vendorUser", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Manager> managerList;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        for (UserRoles role : userRolesList) {
//            authorities.add(new SimpleGrantedAuthority(role.getRole().getName()));
//            // authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getName()));
//        }
            authorities.add(new SimpleGrantedAuthority(userType.getUserTypes()));
        return authorities;
    }

    @Override
    public String getUsername() {
        return phoneNumber;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
