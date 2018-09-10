package com.anizzzz.product.sssweaterhouse.config;

import com.anizzzz.product.sssweaterhouse.utils.ICompresserUtils;
import com.anizzzz.product.sssweaterhouse.utils.impl.JpgCompresserUtil;
import com.anizzzz.product.sssweaterhouse.utils.impl.PngCompresserUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
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
}
