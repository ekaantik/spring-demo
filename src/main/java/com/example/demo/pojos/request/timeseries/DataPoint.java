package com.example.demo.pojos.request.timeseries;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class DataPoint {

    @JsonProperty("alarm_uid")
    private UUID alarmUid;

    @JsonProperty("alarm_id")
    private String alarmId;

    @JsonProperty("alarm_text")
    private String alarmText;

    @JsonProperty("alarm_value")
    private String alarmValue;

    @JsonProperty("asset_id")
    private String assetId;

    @JsonProperty("customer_id")
    private String customerId;

    @JsonProperty("device_category")
    private String deviceCategory;

    @JsonProperty("device_id")
    private String deviceId;

    @JsonProperty("device_location")
    private String deviceLocation;

    @JsonProperty("device_name")
    private String deviceName;

    @JsonProperty("logger_id")
    private String loggerId;

    @JsonProperty("plant_id")
    private String plantId;

    private String severity;

    private String status;

    @JsonProperty("time_zone")
    private String timeZone;

    private ZonedDateTime time;
}

