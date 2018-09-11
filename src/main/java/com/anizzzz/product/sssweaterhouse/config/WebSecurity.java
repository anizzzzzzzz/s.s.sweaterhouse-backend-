package com.anizzzz.product.sssweaterhouse.config;

import com.anizzzz.product.sssweaterhouse.security.JwtAuthenticationEntryPoint;
import com.anizzzz.product.sssweaterhouse.security.JwtAuthenticationFilter;
import com.anizzzz.product.sssweaterhouse.security.jwtutil.JwtTokenUtil;
import com.anizzzz.product.sssweaterhouse.service.serviceImpl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter{
    @Value("${jwt.header}")
    private String tokenHeader;

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public WebSecurity(UserDetailsServiceImpl userDetailsService, JwtTokenUtil jwtTokenUtil, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/user").permitAll()
                .antMatchers(HttpMethod.POST, "/user/resend-token").permitAll()
                .antMatchers(HttpMethod.GET, "/user/activate-user").permitAll()
                .antMatchers(HttpMethod.POST, "/user/send-reset-password-token").permitAll()
                .antMatchers(HttpMethod.POST, "/user/reset-password").permitAll()
                .antMatchers(HttpMethod.POST, "/upload-images").permitAll()
                .antMatchers(HttpMethod.POST,"/find-all-by-type").permitAll()
                .antMatchers(HttpMethod.GET, "/find-all").permitAll()
                .antMatchers(HttpMethod.POST, "/find-one").permitAll()
                .antMatchers(HttpMethod.GET, "/find-all-sales").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterAfter(new JwtAuthenticationFilter(userDetailsService, jwtTokenUtil, tokenHeader),
                        UsernamePasswordAuthenticationFilter.class)
                .headers()
                .frameOptions().sameOrigin()
                .cacheControl();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }
}
