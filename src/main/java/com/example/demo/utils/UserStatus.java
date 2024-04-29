package com.example.demo.utils;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor//(access = AccessLevel.PRIVATE)
public enum UserStatus {
    ENABLED("ENABLED"),    DISABLED("DISABLED");

    private final String userStatus;
}
