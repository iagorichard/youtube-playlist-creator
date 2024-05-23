package com.irrs.youtube.playlist.demo.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class AppConfig {
    
    /**
     * This method is used to create and configure a RestTemplate bean.
     * RestTemplate is a powerful HTTP client for Spring applications.
     * It simplifies the process of making HTTP requests and handling responses.
     *
     * @return a configured RestTemplate instance
     */
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }
}
