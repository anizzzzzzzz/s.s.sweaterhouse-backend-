package com.anizzzz.product.sssweaterhouse.security;

import com.anizzzz.product.sssweaterhouse.security.jwtutil.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private UserDetailsService userDetailsService;
    private JwtTokenUtil jwtTokenUtil;
    private String tokenHeader;

    public JwtAuthenticationFilter(UserDetailsService userDetailsService, JwtTokenUtil jwtTokenUtil, String tokenHeader){
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.tokenHeader = tokenHeader;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        final String requestHeader = request.getHeader(tokenHeader);

        String username=null;
        String authToken=null;

        if(requestHeader != null && requestHeader.startsWith("Bearer ")){
            authToken = requestHeader.substring(7);
            try{
                username = jwtTokenUtil.getUsernameFromToken(authToken);
            }
            catch (IllegalArgumentException ex){
                logger.error("An error occurred during getting username from token :- ",ex.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
            catch (ExpiredJwtException ex){
                logger.warn("The token is expired and not valid anymore :- ", ex.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }

        logger.debug("checking authentication for user '{}'", username);
        if(username != null && username.length()>0 && SecurityContextHolder.getContext().getAuthentication() == null){
            try{
                UserDetails userDetails=userDetailsService.loadUserByUsername(username);

                if(jwtTokenUtil.validateToken(authToken, userDetails)){
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    logger.info("authorised user '{}', setting security context ", username);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            catch (UsernameNotFoundException ex){
                logger.error("User not found '{}'", username);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
        chain.doFilter(request, response);
    }
}
