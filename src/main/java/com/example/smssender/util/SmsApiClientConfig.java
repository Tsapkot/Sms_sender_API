package com.example.smssender.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class SmsApiClientConfig {
    private static final String URL = "http://64934.mocklab.io";

    @Bean
    public WebClient getWebclient() {
        return WebClient.builder()
                .baseUrl(URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
