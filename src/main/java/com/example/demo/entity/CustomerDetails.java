package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "customer_details")
public class CustomerDetails extends BaseUuidEntity implements Serializable {

    @OneToMany(mappedBy = "customerDetails", fetch = FetchType.LAZY, cascade = CascadeType.ALL) // CascadeType.ALL,
    @JsonIgnore
    private List<AssetDetails> assetDetailsList;

    @Column(name = "name")
    @Size(max = 255, message = "Legal Name must be at most 255 characters.")
    private String name;

    @Column(name = "legal_name", unique = true)
    @NotBlank(message = "Legal Name cannot be empty.")
    @NonNull
    @Size(max = 255, message = "Legal Name must be at most 255 characters.")
    private String legalName;

    // TODO : Is customerId Nullable
    @Column(name = "customer_id", unique = true, nullable = false)
    private String customerId;

    @Column(name = "company_email", unique = true, nullable = false)
    @NotBlank(message = "Company email cannot be empty.")
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", flags = Pattern.Flag.CASE_INSENSITIVE,message = "Invalid Email!")
    private String companyEmail;

    @Column(name = "contact_number", nullable = false)
    @Pattern(regexp = "(^$|[0-9]{10})")
    @NotBlank(message = "Contact number cannot be empty.")
    private String contactNumber;

    @Column(name = "admin_email", unique = true, nullable = false)
    @NotBlank(message = "Admin email cannot be empty.")
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", flags = Pattern.Flag.CASE_INSENSITIVE,message = "Invalid Email!")
    private String adminEmail;

    @Column(name = "address")
    @Size(max = 1000, message = "Legal Name must be at most 1000 characters.")
    private String address;

    @Column(name = "invite_status")
    @Enumerated(EnumType.STRING)
    @NonNull
    private CustomerInviteStatus customerInviteStatus;

    @Column(name = "service_status")
    @Enumerated(EnumType.STRING)
    @NonNull
    private CustomerServiceStatus customerServiceStatus;

}
