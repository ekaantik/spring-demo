package com.example.demo.security.dto;

import com.example.demo.utils.UserStatus;
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
    private String email;
    private String password;
    private UserStatus userStatus;
    private String contactNumber;
}
