package com.example.demo.constants;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum VideoTypes {
    MP4("MP4");

    private final String videoTypes;
}
