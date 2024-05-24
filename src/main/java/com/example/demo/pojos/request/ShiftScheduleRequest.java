package com.example.demo.pojos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ShiftScheduleRequest {
    @NotNull
    private UUID storeId;
    @NotNull
    private UUID shiftId;
    @NotNull
    private String shiftName;
    private LocalDate date;
    private ZonedDateTime startTime;
    private ZonedDateTime endTime;
}
