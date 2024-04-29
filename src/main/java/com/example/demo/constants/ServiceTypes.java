package com.example.demo.constants;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ServiceTypes {
    USER("USER"), DASHBOARD("DASHBOARD"), INFLUX("INFLUX"),
    WRITE_API("WRITE_API");

    private final String serviceTypes;
}
