package com.vannguyen.jobseeker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizeException extends RuntimeException {
    public UnauthorizeException() {
        super("You are not logged");
    }
}
