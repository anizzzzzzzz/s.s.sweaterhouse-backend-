package com.anizzzz.product.sssweaterhouse.service.user.impl;

import com.anizzzz.product.sssweaterhouse.exceptionHandling.exceptions.AccountNotActivatedException;
import com.anizzzz.product.sssweaterhouse.exceptionHandling.exceptions.AuthenticationException;
import com.anizzzz.product.sssweaterhouse.security.jwtutil.JwtTokenUtil;
import com.anizzzz.product.sssweaterhouse.security.jwtutil.JwtUser;
import com.anizzzz.product.sssweaterhouse.security.jwtutil.dto.JwtAuthRequest;
import com.anizzzz.product.sssweaterhouse.security.jwtutil.dto.JwtAuthResponse;
import com.anizzzz.product.sssweaterhouse.service.user.IAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Service
public class AuthenticationService implements IAuthenticationService {
    @Value("${jwt.header}")
    private String authHeader;

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public AuthenticationService(AuthenticationManager authenticationManager, @Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService, JwtTokenUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public JwtAuthResponse createAuthentication(JwtAuthRequest jwtAuthRequest) {
        authenticate(jwtAuthRequest.getUsername().toLowerCase(), jwtAuthRequest.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(jwtAuthRequest.getUsername().toLowerCase());
        return new JwtAuthResponse(jwtTokenUtil.generateToken(userDetails), userDetails.getUsername());
    }

    @Override
    public JwtAuthResponse createAuthenticationForSocialLogin(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username.toLowerCase());
        return new JwtAuthResponse(jwtTokenUtil.generateToken(userDetails), username);
    }

    @Override
    public JwtAuthResponse refreshAndSendAuthenticationToken(HttpServletRequest request) {
        String token = request.getHeader(authHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);

        if(jwtTokenUtil.canTokenBeRefreshed(token, user.getPasswordStamp())){
            return new JwtAuthResponse(jwtTokenUtil.refreshToken(token), username);
        } else{
            return null;
        }
    }

    private void authenticate(String username, String password) {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException ex){
            throw new AccountNotActivatedException("Users account not activated",ex);
        } catch (InternalAuthenticationServiceException | BadCredentialsException ex){
            throw new AuthenticationException("Either username or password is incorrect", ex);
        }
    }
}
