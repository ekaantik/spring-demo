package com.example.demo.constants;

import com.example.demo.exception.GenericResponse;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;

@UtilityClass
public class Constants {

    public static final String UNEXPECTED_ERROR_MSG = "Unexpected Error Occured!";

    public static final String FIELD_ID = "id";

    public static final String TABLE_VENDOR = "Vendor";
    public static final String TABLE_MANAGER = "Manager";
    public static final String TABLE_USER = "User";
    public static final String TABLE_TECHNICIAN = "Technician";
    public static final String TABLE_STORE = "Store";
    public static final String TABLE_SHIFT = "Shift";
    public static final String TABLE_SHIFT_SCHEDULE = "ShiftSchedule";

    public static final GenericResponse SUCCESS_RESPONSE = new GenericResponse(null, "SUCCESS",HttpStatus.CREATED.value(), null);
}
