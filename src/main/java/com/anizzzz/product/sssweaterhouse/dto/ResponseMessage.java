package com.anizzzz.product.sssweaterhouse.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ResponseMessage {
    private String firstName;
    private String username;
    private String message;
    private HttpStatus httpStatus;
    private ProductResponse productResponse;

    public ResponseMessage(String message, HttpStatus httpStatus){
        this.message=message;
        this.httpStatus=httpStatus;
    }

    public ResponseMessage(String message, ProductResponse productResponse, HttpStatus httpStatus){
        this.message=message;
        this.productResponse=productResponse;
        this.httpStatus=httpStatus;
    }
}
