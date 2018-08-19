package com.anizzzz.product.sssweaterhouse.exceptionHandling.dto;

import lombok.Data;

@Data
public class ErrorResponse {
    private int errorCode;
    private String message;
}
