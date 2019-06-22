package com.anizzzz.product.sssweaterhouse.security.jwtutil.dto;

import java.util.List;

public class JwtAuthResponse {
    private String token;
    private String username;
    private List<String> roles;

    public JwtAuthResponse(){}

    public JwtAuthResponse(String token, String username){
        this.token = token;
        this.username = username;
    }

    public JwtAuthResponse(String token, String username, List<String> roles) {
        this.token = token;
        this.username = username;
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
