package com.example.demo.pojos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ShiftRequest {
    @NotNull
    private UUID storeId;
    @NotNull
    private String shiftName;
    private LocalTime startTime;
    private LocalTime  endTime;
}
