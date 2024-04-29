package com.example.demo.pojos.request;

import com.example.iot.utils.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class UserProfileRequest {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private UserStatus userStatus;
    private String contactNumber;

}
