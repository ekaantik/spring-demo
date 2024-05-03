package com.example.demo.security.entity;


import com.example.demo.constants.UserType;
import com.example.demo.entity.base.BaseUuidEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
@Table(name = "user")
public class User extends BaseUuidEntity implements UserDetails {

    @Column(name = "first_name")
    @NonNull
    @Size(max = 255, message = "First Name must be at most 255 characters.")
    private String firstName;

    @Column(name = "last_name")
    @NonNull
    @Size(max = 255, message = "Last Name must be at most 255 characters.")
    private String lastName;

    @Column
    @NonNull
    @Size(max = 255, message = "password Name must be at most 255 characters.")
    private String password;

    @Column(name = "phone_number", unique = true)
    @NonNull
    @Pattern(regexp = "(^$|[0-9]{10})")
    private String phoneNumber;

    @Column(name = "type", unique = true)
    @Enumerated
    private UserType userType;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_"+userType.getUserTypes()));
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
