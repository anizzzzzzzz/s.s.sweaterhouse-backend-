package com.anizzzz.product.sssweaterhouse.exceptionHandling.exceptions;

public class ProductException extends RuntimeException {
    public ProductException(String message, Throwable cause){
        super(message,cause);
    }
}
