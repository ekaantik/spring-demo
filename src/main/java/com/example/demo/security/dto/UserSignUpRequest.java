package com.example.demo.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class UserSignUpRequest {
    private String firstName;
    private String lastName;
    private String password;
    private String phoneNumber;
}
