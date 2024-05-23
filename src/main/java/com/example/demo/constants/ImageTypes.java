package com.example.demo.constants;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ImageTypes {
    JPG("JPG"), JPEG("JPEG"), MANAGER("MANAGER"), TECHNICIAN("TECHNICIAN");

    private final String userTypes;
}
