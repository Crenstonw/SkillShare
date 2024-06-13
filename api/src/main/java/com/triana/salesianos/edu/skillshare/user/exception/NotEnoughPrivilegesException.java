package com.triana.salesianos.edu.skillshare.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

import java.time.Instant;

public class NotEnoughPrivilegesException extends ErrorResponseException {
    public NotEnoughPrivilegesException() {
        super(HttpStatus.FORBIDDEN, of("Not Enough privileges"), null);
    }

    public static ProblemDetail of(String message) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, message);
        problemDetail.setTitle("Not Enough privileges");
        problemDetail.setProperty("entityType", "Note");
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }
}
