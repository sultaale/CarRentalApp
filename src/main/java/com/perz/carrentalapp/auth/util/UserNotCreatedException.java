package com.perz.carrentalapp.auth.util;

public class UserNotCreatedException extends RuntimeException{

    public UserNotCreatedException(String message){
        super(message);
    }
}
