package com.example.demo.pojos.validations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ExceptionMessageDto extends Exception {

    private final String message;

}
