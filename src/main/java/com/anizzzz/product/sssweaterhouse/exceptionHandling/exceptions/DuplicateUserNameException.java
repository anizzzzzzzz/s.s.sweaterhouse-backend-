package com.anizzzz.product.sssweaterhouse.exceptionHandling.exceptions;

public class DuplicateUserNameException extends RuntimeException {
    public DuplicateUserNameException(){super("Account with given username already exist. Cannot create account");}
}
