package com.example.demo.pojos.request;

import java.util.UUID;

import com.example.demo.constants.ShiftType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ShiftRequest {
    
    private UUID storeId;
    private ShiftType shiftType;
}
