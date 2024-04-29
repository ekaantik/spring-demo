package com.example.demo.exception;


import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Details implements Serializable {

    @Serial
    private static final long serialVersionUID = 6882328762410998687L;

    private String appError;
    private String appErrorCode;
    private String appErrorMessage;
}
