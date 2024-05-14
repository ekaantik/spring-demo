package com.example.demo.pojos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ShiftRequest {
    private UUID storeId;
    private String shiftName;
    private LocalTime startTime;
    private LocalTime  endTime;
}
