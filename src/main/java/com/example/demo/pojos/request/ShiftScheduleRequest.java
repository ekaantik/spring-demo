package com.example.demo.pojos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ShiftScheduleRequest {
    private UUID storeId;
    private UUID shiftId;
    private String shiftName;
    private LocalDate date;
    private ZonedDateTime startTime;
    private ZonedDateTime endTime;
}
