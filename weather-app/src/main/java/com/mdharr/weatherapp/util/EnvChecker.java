package com.mdharr.weatherapp.util;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EnvChecker {

    @Value("${api.key}")
    private String apiKey;

    @PostConstruct
    public void init() {
        System.out.println("API Key: " + apiKey);
    }
}
