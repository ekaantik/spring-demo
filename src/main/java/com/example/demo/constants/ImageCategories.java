package com.example.demo.constants;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ImageCategories {
    LICENSE("LICENSE"), IDENTITY_CARD("IDENTITY_CARD"), LOGO("LOGO"), IMAGE_COVER("IMAGE_COVER");

    private final String imageCategories;
}
