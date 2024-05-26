package com.triana.salesianos.edu.skillshare.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

import java.time.Instant;

public class CannotModifyPrivileges extends ErrorResponseException {

    public CannotModifyPrivileges() {

        super(HttpStatus.FORBIDDEN, of("You cannot modify your own privileges"), null);
    }

    public static ProblemDetail of(String message) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, message);
        problemDetail.setTitle("You cannot modify your own privileges");
        problemDetail.setProperty("entityType", "Note");
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }
}
