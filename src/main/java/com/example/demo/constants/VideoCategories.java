package com.example.demo.constants;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum VideoCategories {
    IMAGE_COVER("image_cover");

    private final String videoCategories;
}
