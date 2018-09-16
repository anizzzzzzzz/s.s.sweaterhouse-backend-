package com.anizzzz.product.sssweaterhouse.service;

import com.anizzzz.product.sssweaterhouse.security.jwtutil.dto.JwtAuthRequest;
import com.anizzzz.product.sssweaterhouse.security.jwtutil.dto.JwtAuthResponse;

import javax.servlet.http.HttpServletRequest;

public interface IAuthenticationService {
    JwtAuthResponse createAuthentication(JwtAuthRequest jwtAuthRequest);

    JwtAuthResponse createAuthenticationForSocialLogin();

    JwtAuthResponse refreshAndSendAuthenticationToken(HttpServletRequest request);
}
