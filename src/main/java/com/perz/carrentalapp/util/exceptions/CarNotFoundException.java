package com.perz.carrentalapp.util.exceptions;

public class CarNotFoundException extends RuntimeException{

    public CarNotFoundException(String message){
        super(message);
    }
}
