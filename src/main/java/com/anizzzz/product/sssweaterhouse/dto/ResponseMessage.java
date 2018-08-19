package com.anizzzz.product.sssweaterhouse.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ResponseMessage {
    private String firstName;
    private String username;
    private String message;
    private HttpStatus httpStatus;

    public ResponseMessage(String message, HttpStatus httpStatus){
        this.message=message;
        this.httpStatus=httpStatus;
    }
}
