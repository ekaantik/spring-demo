package com.example.demo.constants;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ImageCategories {
    LICENSE("license"), IDENTITY_CARD("identity_card"), LOGO("logo"), IMAGE_COVER("image_cover");

    private final String imageCategories;
}
