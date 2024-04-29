package com.example.demo.constants;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum UserRolesDefault {
    USER("USER"), WEB_ADMIN("WEB_ADMIN"), CUSTOMER_ADMIN("CUSTOMER_ADMIN");

    private final String userRoles;
}
