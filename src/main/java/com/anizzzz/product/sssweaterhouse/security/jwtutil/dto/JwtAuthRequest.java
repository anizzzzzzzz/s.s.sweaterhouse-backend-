package com.anizzzz.product.sssweaterhouse.security.jwtutil.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthRequest {
    private String username;
    private String password;
}
