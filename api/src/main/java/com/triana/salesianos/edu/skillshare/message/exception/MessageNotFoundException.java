package com.triana.salesianos.edu.skillshare.message.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

import java.net.URI;
import java.time.Instant;

public class MessageNotFoundException extends ErrorResponseException {
    public MessageNotFoundException() {

        super(HttpStatus.NOT_FOUND, of("Message not found"), null);
    }

    public static ProblemDetail of(String message) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, message);
        problemDetail.setTitle("Message not found");
        problemDetail.setType(URI.create("https://api.midominio.com/errors/user-not-found"));
        problemDetail.setProperty("entityType", "Note");
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }
}
