package com.triana.salesianos.edu.skillshare.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

import java.time.Instant;

public class FavoriteInterpolationNotPosibleException extends ErrorResponseException {

    public FavoriteInterpolationNotPosibleException() {
        super(HttpStatus.CONFLICT, of("Favorite interpolation not possible"), null);
    }

    public static ProblemDetail of(String message) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, message);
        problemDetail.setTitle("Favorite interpolation not possible");
        problemDetail.setProperty("entityType", "Note");
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }
}
