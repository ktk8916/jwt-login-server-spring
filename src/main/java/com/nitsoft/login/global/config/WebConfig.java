package com.nitsoft.login.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@EnableWebMvc
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${cors.allow-origin}")
    private List<String> allowOrigin;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods(
                        HttpMethod.GET.name(),
                        HttpMethod.POST.name()
                )
                .allowedOrigins(allowOrigin.toArray(new String[0]))
                .allowCredentials(true)
        ;
    }
}
