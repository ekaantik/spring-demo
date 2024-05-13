package com.example.demo.constants;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum VideoCategories {
    IMAGE_COVER("IMAGE_COVER");

    private final String videoCategories;
}
