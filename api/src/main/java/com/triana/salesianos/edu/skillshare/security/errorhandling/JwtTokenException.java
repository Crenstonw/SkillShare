package com.triana.salesianos.edu.skillshare.security.errorhandling;

public class JwtTokenException extends RuntimeException{

    public JwtTokenException(String msg){
        super(msg);
    }

}
