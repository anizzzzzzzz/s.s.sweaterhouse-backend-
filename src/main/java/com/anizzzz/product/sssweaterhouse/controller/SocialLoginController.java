package com.anizzzz.product.sssweaterhouse.controller;

import com.anizzzz.product.sssweaterhouse.security.jwtutil.dto.JwtAuthResponse;
import com.anizzzz.product.sssweaterhouse.service.IAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
public class SocialLoginController {
    @Value("${route.social.login.success}")
    private String success;
    @Value("${route.social.login.error}")
    private String error;

    private final IAuthenticationService iAuthenticationService;

    @Autowired
    public SocialLoginController(IAuthenticationService iAuthenticationService) {
        this.iAuthenticationService = iAuthenticationService;
    }

    // #TODO need to specify where to redirect after success and error while social login
    @GetMapping(value = "${route.social.login.success}")
    public JwtAuthResponse success(Principal principal){
        JwtAuthResponse response = iAuthenticationService.createAuthenticationForSocialLogin(principal.getName());
        return  response;
    }

    @GetMapping(value = "${route.social.login.error}")
    public String failure(HttpServletRequest request){
        return "redirect:"+request.getScheme()+"://"+request.getServerName()+":3000"+"/social-login-redux?status=400";
    }
}
