package com.triana.salesianos.edu.skillshare.order.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

import java.net.URI;
import java.time.Instant;

public class NoOrderException extends ErrorResponseException {

    public NoOrderException() {

        super(HttpStatus.NOT_FOUND, of("Order not found or not enough privileges"), null);
    }

    public static ProblemDetail of(String message) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, message);
        problemDetail.setTitle("Order not found or not enough privileges");
        problemDetail.setType(URI.create("https://api.midominio.com/errors/user-not-found"));
        problemDetail.setProperty("entityType", "Note");
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }
}
