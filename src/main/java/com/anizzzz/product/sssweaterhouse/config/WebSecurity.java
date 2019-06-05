package com.anizzzz.product.sssweaterhouse.config;

import com.anizzzz.product.sssweaterhouse.security.JwtAuthenticationEntryPoint;
import com.anizzzz.product.sssweaterhouse.security.JwtAuthenticationFilter;
import com.anizzzz.product.sssweaterhouse.security.jwtutil.JwtTokenUtil;
import com.anizzzz.product.sssweaterhouse.service.user.impl.UserDetailsServiceImpl;
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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter{
    @Value("${jwt.header}")
    private String tokenHeader;
    @Value("${route.social.login.google}")
    private String googleRoute;
    @Value("${route.social.login.facebook}")
    private String facebookRoute;
    @Value("${route.social.login.error}")
    private String errorRoute;
    @Value("${route.social.login.success}")
    private String socialRedirectUrl;

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
                .antMatchers(HttpMethod.POST, "/auth").permitAll()
                .antMatchers(HttpMethod.POST, "/register/user").permitAll()
                .antMatchers(HttpMethod.POST, "/user/resend-token").permitAll()
                .antMatchers(HttpMethod.GET, "/user/activate-user").permitAll()
                .antMatchers(HttpMethod.POST, "/user/send-reset-password-token").permitAll()
                .antMatchers(HttpMethod.POST, "/user/reset-password").permitAll()
                .antMatchers(HttpMethod.POST, "/upload-products").permitAll()
                .antMatchers(HttpMethod.POST,"/find-all-by-type").permitAll()
                .antMatchers(HttpMethod.GET, "/find-all").permitAll()
                .antMatchers(HttpMethod.POST, "/find-one").permitAll()
                .antMatchers(HttpMethod.GET, "/find-all-sales").permitAll()
                .antMatchers(HttpMethod.GET, "/find-all-sales-and-type").permitAll()
                .antMatchers(HttpMethod.GET,socialRedirectUrl).permitAll()
                .antMatchers(errorRoute).permitAll()
                .anyRequest().authenticated()
                .and()
                .apply(getSpringSocialConfigurer())
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
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

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    @Override
    public UserDetailsService userDetailsService() {
        return userDetailsService;
    }

    @Override
    public void configure(org.springframework.security.config.annotation.web.builders.WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
    }

    private SpringSocialConfigurer getSpringSocialConfigurer() {
        SpringSocialConfigurer config = new SpringSocialConfigurer();
        config.signupUrl(errorRoute);
        config.alwaysUsePostLoginUrl(true);
        config.postLoginUrl(socialRedirectUrl);

        return config;
    }
}
