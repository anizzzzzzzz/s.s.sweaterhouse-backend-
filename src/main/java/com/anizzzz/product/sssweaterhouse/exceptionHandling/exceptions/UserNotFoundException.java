package com.anizzzz.product.sssweaterhouse.exceptionHandling.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String format){
        super("UserRole not found.");
    }
}
