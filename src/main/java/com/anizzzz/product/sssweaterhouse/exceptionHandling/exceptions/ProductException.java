package com.anizzzz.product.sssweaterhouse.exceptionHandling.exceptions;

public class ProductException extends RuntimeException {
    public ProductException(){
        super("Problem occured while saving product.");
    }
}
