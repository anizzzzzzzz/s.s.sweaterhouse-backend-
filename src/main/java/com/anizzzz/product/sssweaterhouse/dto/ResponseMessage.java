package com.anizzzz.product.sssweaterhouse.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ResponseMessage {
    private String firstName;
    private String username;
    private String message;
    private HttpStatus httpStatus;
    private ProductResponse productResponse;

    public ResponseMessage(){}

    public ResponseMessage(String message, HttpStatus httpStatus){
        this.message=message;
        this.httpStatus=httpStatus;
    }

    public ResponseMessage(String message, ProductResponse productResponse, HttpStatus httpStatus){
        this.message=message;
        this.productResponse=productResponse;
        this.httpStatus=httpStatus;
    }

    public ResponseMessage(String firstName, String username, String message, HttpStatus httpStatus, ProductResponse productResponse) {
        this.firstName = firstName;
        this.username = username;
        this.message = message;
        this.httpStatus = httpStatus;
        this.productResponse = productResponse;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public ProductResponse getProductResponse() {
        return productResponse;
    }

    public void setProductResponse(ProductResponse productResponse) {
        this.productResponse = productResponse;
    }
}
