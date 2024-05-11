package com.example.demo.pojos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TechnicianRequest {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String password;
}
