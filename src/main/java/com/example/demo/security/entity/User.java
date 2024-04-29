package com.example.demo.security.entity;


import com.example.demo.entity.CustomerDetails;
import com.example.demo.entity.UserRoles;
import com.example.demo.entity.base.BaseUuidEntity;
import com.example.demo.utils.UserStatus;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString
@SuperBuilder
@Entity
@Table(name = "user_details")
public class User extends BaseUuidEntity implements UserDetails {

    @ManyToOne
    @JoinColumn(name = "customer_details_id")
    // @JsonBackReference
    @ToString.Exclude
    private CustomerDetails customerDetails;

    @Column(name = "first_name")
    @Size(max = 255, message = "First Name must be at most 255 characters.")
    private String firstName;

    @Column(name = "last_name")
    @Size(max = 255, message = "Last Name must be at most 255 characters.")
    private String lastName;

    // TODO : Move message to constant
    @Column(name = "email", unique = true, nullable = false)
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Invalid Email!")
    private String email;

    @Column // (nullable = false)
    @Size(max = 255, message = "Last Name must be at most 255 characters.")
    private String password;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @Column(name = "contact_number", unique = true)
    @Pattern(regexp = "(^$|[0-9]{10})")
    private String contactNumber;

    @OneToMany(mappedBy = "user" ,cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    @ToString.Exclude
    private List<UserRoles> userRolesList = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (UserRoles role : userRolesList) {
            authorities.add(new SimpleGrantedAuthority(role.getRole().getName()));
            // authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getName()));
        }
        return authorities;
    }

//    public void addUserRoles(Role role) {
//        this.roleList.add(role);
//    }
    public void addUserRoles(UserRoles userRoles) {
        this.userRolesList.add(userRoles);
    }

    @Override
    public String getUsername() {
        return email;
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
