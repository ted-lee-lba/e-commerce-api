package com.sample.ecommerce.config;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    @Bean
    public ObjectMapper buildObjectMapper() {
       return new ObjectMapper().setSerializationInclusion(Include.NON_NULL);
    }
}
