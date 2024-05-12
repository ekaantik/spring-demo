package com.example.demo.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ShiftType {
    FULL_DAY(10, 22),
    DAY_SHIFT(10, 20),
    NIGHT_SHIFT(20, 10);

    private final int startTime;
    private final int endTime;

}
