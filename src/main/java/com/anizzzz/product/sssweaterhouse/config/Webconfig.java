package com.anizzzz.product.sssweaterhouse.config;

import com.anizzzz.product.sssweaterhouse.security.jwtutil.JwtTokenUtil;
import com.anizzzz.product.sssweaterhouse.security.jwtutil.JwtUserFactory;
import com.anizzzz.product.sssweaterhouse.utils.ICompresserUtils;
import com.anizzzz.product.sssweaterhouse.utils.impl.JpgCompresserUtil;
import com.anizzzz.product.sssweaterhouse.utils.impl.PngCompresserUtil;
import com.anizzzz.product.sssweaterhouse.validation.UserValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableSpringDataWebSupport
public class Webconfig implements WebMvcConfigurer {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD");
            }
        };
    }

    @Bean
    public ICompresserUtils jpgCompresser(){
        return new JpgCompresserUtil();
    }

    @Bean
    public ICompresserUtils pngCompresser(){
        return new PngCompresserUtil();
    }

    @Bean
    public JwtTokenUtil jwtTokenUtil(){return new JwtTokenUtil();}

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){return new BCryptPasswordEncoder();}

    @Bean
    public UserValidator beforeCreateUserValidator(){return new UserValidator();}
}
