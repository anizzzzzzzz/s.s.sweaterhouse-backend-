package com.anizzzz.product.sssweaterhouse.exceptionHandling.exceptions;

public class EmailException extends RuntimeException {
    public EmailException(String message, Throwable cause){
        super(message,cause);
    }
}
