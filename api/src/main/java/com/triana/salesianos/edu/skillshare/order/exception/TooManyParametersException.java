package com.triana.salesianos.edu.skillshare.order.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

import java.net.URI;
import java.time.Instant;

public class TooManyParametersException extends ErrorResponseException {
    public TooManyParametersException() {

        super(HttpStatus.EXPECTATION_FAILED, of("You can only use 1 or none parameter at a time"), null);
    }

    public static ProblemDetail of(String message) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.EXPECTATION_FAILED, message);
        problemDetail.setTitle("You can only use 1 parameter at a time");
        problemDetail.setType(URI.create("https://api.midominio.com/errors/user-not-found"));
        problemDetail.setProperty("entityType", "Note");
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }
}
