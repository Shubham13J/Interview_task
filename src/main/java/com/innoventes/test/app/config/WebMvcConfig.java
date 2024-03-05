package com.innoventes.test.app.config;

import com.innoventes.test.app.interceptor.WebSiteURLValidationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new WebSiteURLValidationInterceptor())
                .addPathPatterns("/api/companies");
    }
}
