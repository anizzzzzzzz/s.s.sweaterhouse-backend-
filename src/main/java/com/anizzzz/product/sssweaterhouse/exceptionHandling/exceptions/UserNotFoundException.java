package com.anizzzz.product.sssweaterhouse.exceptionHandling.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(){
        super("User not found.");
    }
}
