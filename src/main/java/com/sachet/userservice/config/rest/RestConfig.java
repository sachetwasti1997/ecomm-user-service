package com.sachet.userservice.config.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfig {

    @Bean(value = "NoSSLRestTemplate")
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

}
