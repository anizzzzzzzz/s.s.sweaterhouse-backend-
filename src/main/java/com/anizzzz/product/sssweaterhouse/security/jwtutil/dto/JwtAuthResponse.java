package com.anizzzz.product.sssweaterhouse.security.jwtutil.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
public class JwtAuthResponse {
    private String token;
    private String username;
    private List<String> roles;

    public JwtAuthResponse(){}

    public JwtAuthResponse(String token, String username){
        this.token = token;
        this.username = username;
    }
}
