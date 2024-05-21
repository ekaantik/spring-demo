package com.example.demo.pojos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.UUID;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ShiftResponse implements Serializable {
    private UUID id;
    private UUID storeId;
    private String shiftName;
    private LocalTime startTime;
    private LocalTime endTime;
}
