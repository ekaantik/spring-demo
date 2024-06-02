package com.example.demo.pojos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserResponse implements Serializable {

    private String id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String userType;
}
