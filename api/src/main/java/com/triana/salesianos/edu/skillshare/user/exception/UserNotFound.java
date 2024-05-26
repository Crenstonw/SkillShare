package com.triana.salesianos.edu.skillshare.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

import java.time.Instant;

public class UserNotFound extends ErrorResponseException {
    public UserNotFound() {
        super(HttpStatus.FORBIDDEN, of("User not found"), null);
    }

    public static ProblemDetail of(String message) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, message);
        problemDetail.setTitle("User not found");
        problemDetail.setProperty("entityType", "Note");
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }
}
