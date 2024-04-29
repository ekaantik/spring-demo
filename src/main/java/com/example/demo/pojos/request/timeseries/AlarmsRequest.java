package com.example.demo.pojos.request.timeseries;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.ZonedDateTime;
import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class AlarmsRequest {
    private String customerId;
    private ArrayList<String> assetId;
    private ZonedDateTime startTime;
    private ZonedDateTime endTime;
    private String timeMetrics;
}
