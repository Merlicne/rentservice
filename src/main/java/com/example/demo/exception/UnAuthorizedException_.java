package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class UnAuthorizedException_ extends RuntimeException {
    public UnAuthorizedException_(String message) {
        super(message);
    }

}
