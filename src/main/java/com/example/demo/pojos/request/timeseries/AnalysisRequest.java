package com.example.demo.pojos.request.timeseries;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AnalysisRequest {
    private ZonedDateTime startTime;
    private ZonedDateTime endTime;
    private String customerId;
    private Map<String, List<String>> requestData;
}