package com.example.demo.constants;

import com.example.demo.exception.GenericResponse;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;

@UtilityClass
public class Constants {
    public static final String UNEXPECTED_ERROR_MSG = "Unexpected Error Occured!";
    public static final GenericResponse SUCCESS_RESPONSE = new GenericResponse(null, "SUCCESS", HttpStatus.CREATED.value(), null);
}
