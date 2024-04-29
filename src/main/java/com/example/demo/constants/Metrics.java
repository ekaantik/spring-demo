package com.example.demo.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Metrics {

    POWER("power"),
    ACTIVE_POWER("active_power"),
    ENERGY("energy"),
    TOTAL_ENERGY("total_energy"),
    REVENUE("revenue"),
    EMISSION_CO2("emissionCo2"),
    CUSTOM("custom");

    private final String metricsName;

//    Metrics(String metricsName) {
//        this.metricsName = metricsName;
//    }

}
