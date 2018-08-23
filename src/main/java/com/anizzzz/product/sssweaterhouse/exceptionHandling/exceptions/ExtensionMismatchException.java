package com.anizzzz.product.sssweaterhouse.exceptionHandling.exceptions;

public class ExtensionMismatchException extends RuntimeException {
    public ExtensionMismatchException(){
        super("Extension doesn't match");
    }
}
