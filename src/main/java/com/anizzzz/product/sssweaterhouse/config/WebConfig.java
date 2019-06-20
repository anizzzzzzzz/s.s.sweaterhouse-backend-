package com.anizzzz.product.sssweaterhouse.config;

import com.anizzzz.product.sssweaterhouse.interceptor.LoginInterceptor;
import com.anizzzz.product.sssweaterhouse.security.jwtutil.JwtTokenUtil;
import com.anizzzz.product.sssweaterhouse.utils.ICompresserUtils;
import com.anizzzz.product.sssweaterhouse.utils.PageSerializer;
import com.anizzzz.product.sssweaterhouse.utils.impl.JpgCompresserUtil;
import com.anizzzz.product.sssweaterhouse.utils.impl.PngCompresserUtil;
import com.anizzzz.product.sssweaterhouse.validation.UserValidator;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@Configuration
@EnableSpringDataWebSupport
public class WebConfig extends WebMvcConfigurerAdapter {
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

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor());
    }

    //  -------------Configuring JsonView In Page-----------
    @Bean
    public Module jacksonPageWithJsonViewModule() {
        SimpleModule module = new SimpleModule("jackson-page-with-jsonview", Version.unknownVersion());
        module.addSerializer(PageImpl.class, new PageSerializer());
        return module;
    }

    /*
     * This allows to start pagination from 1
     * ie; /{url}?page=1
     * */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
        resolver.setOneIndexedParameters(true);
        argumentResolvers.add(resolver);
        super.addArgumentResolvers(argumentResolvers);
    }
}
