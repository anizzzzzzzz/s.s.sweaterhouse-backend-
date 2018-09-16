package com.anizzzz.product.sssweaterhouse.controller;

import com.anizzzz.product.sssweaterhouse.dto.ResponseMessage;
import com.anizzzz.product.sssweaterhouse.security.jwtutil.dto.JwtAuthRequest;
import com.anizzzz.product.sssweaterhouse.security.jwtutil.dto.JwtAuthResponse;
import com.anizzzz.product.sssweaterhouse.service.serviceImpl.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AuthController {
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("${jwt.route.authentication.path}")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthRequest jwtAuthRequest){
        return ResponseEntity.ok(authenticationService.createAuthentication(jwtAuthRequest));
    }

    @PostMapping("${jwt.route.authentication.refresh}")
    public ResponseEntity<?> refreshToken(HttpServletRequest request){
        JwtAuthResponse response = authenticationService.refreshAndSendAuthenticationToken(request);
        if(response!=null)
            return ResponseEntity.ok(response);
        else
            return new ResponseEntity<Object>(
                    new ResponseMessage("Token cannot be refreshed", HttpStatus.NOT_ACCEPTABLE),
                    HttpStatus.NOT_ACCEPTABLE);
    }
}
