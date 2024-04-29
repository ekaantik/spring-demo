package com.example.demo.pojos.request.timeseries;

import com.example.iot.constants.Metrics;
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
public class MetricsRequest {

    private String customerId;
    private ArrayList<String> plantId;
    private ArrayList<String> deviceId;
    private ArrayList<String> deviceCategory;
    private ArrayList<String> sensorCategory;
    private ArrayList<Metrics> metrics;
    private ZonedDateTime startTime;
    private ZonedDateTime endTime;
    private String timeMetrics;
    
}
