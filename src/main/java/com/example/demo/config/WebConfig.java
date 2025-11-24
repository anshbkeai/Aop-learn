package com.example.demo.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig  implements WebMvcConfigurer{

    @Bean
    public FilterRegistrationBean<CorrelationFilter> correlationIdFilter() {
        FilterRegistrationBean<CorrelationFilter> reg = new FilterRegistrationBean<>();
        reg.setFilter(new CorrelationFilter());
        reg.addUrlPatterns("/*");  // apply to all requests
        reg.setOrder(1);           // before anything else
        return reg;
    }
}
