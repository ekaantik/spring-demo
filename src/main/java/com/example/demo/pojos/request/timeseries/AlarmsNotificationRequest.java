package com.example.demo.pojos.request.timeseries;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AlarmsNotificationRequest {
    private String token ;
    private ArrayList<DataPoint> dataPointsList;
}
