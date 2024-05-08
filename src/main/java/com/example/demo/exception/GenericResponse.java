package com.example.demo.exception;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Getter
@Setter
@ToString
@Builder
public class GenericResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 8523287455962400758L;

    private final ZonedDateTime timestamp;
    private final String message;
    private final Integer responseCode;
    private final Details details;

    public GenericResponse() {
        super();
        this.timestamp = null;
        this.message = null;
        this.responseCode = null;
        this.details = null;
    }

    public GenericResponse(ZonedDateTime timestamp, String message, Integer responseCode, Details details) {
        this.timestamp = timestamp;
        this.message = message;
        this.responseCode = responseCode;
        this.details = details;
    }

}
