package com.example.demo.constants;

import com.example.demo.exception.GenericResponse;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;

@UtilityClass
public class Constants {


    public static final String UNEXPECTED_ERROR_MSG = "Unexpected Error Occured!";
    public static final String CURRENT = "CURRENT";
    public static final String ENERGY = "ENERGY";
    public static final String POWER = "POWER";
    public static final String TEMPERATURE = "TEMPERATURE";
    public static final String ANALOG_INPUTS = "ANALOG INPUTS";
    public static final String VOLTAGE = "VOLTAGE";
    public static final String FREQUENCY = "FREQUENCY";
    public static final String SCB_STATUS = "SCB_STATUS";
    public static final String REACTIVE_POWER = "REACTIVE POWER";
    public static final String APARENT_POWER = "APARENT POWER";
    public static final String POWER_FACTOR = "POWER FACTOR";
    public static final String WIND_DIRECTION = "WIND DIRECTION";
    public static final String WIND_SPEED = "WIND SPEED";
    public static final String HUMIDITY = "HUMIDITY";
    public static final String PRESSURE = "PRESSURE";
    public static final String IRRADIANCE = "IRRADIANCE";
    public static final String TIME = "TIME";
    public static final String WEATHER_CONDITION = "WEATHER CONDITION";
    public static final String KPI = "KPI";
    public static final GenericResponse SUCCESS_RESPONSE = new GenericResponse(null, "SUCCESS", HttpStatus.CREATED.value(), null);
}
