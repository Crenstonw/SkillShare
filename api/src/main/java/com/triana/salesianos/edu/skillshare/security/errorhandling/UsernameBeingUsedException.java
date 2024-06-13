package com.triana.salesianos.edu.skillshare.security.errorhandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

import java.time.Instant;

public class UsernameBeingUsedException extends ErrorResponseException {

    public UsernameBeingUsedException() {

        super(HttpStatus.BAD_REQUEST, of("Username is being used"), null);
    }

    public static ProblemDetail of(String message) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, message);
        problemDetail.setTitle("Username is being used");
        problemDetail.setProperty("entityType", "Note");
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }
}
