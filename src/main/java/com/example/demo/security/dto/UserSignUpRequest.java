package com.example.demo.security.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class UserSignUpRequest {
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull

    private String password;
    @NotNull
    @Pattern(regexp = "(^$|[0-9]{10})")
    private String phoneNumber;
}
