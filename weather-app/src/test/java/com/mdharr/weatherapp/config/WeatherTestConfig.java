package com.mdharr.weatherapp.config;

import com.mdharr.weatherapp.service.WeatherService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class WeatherTestConfig {

    @Bean
    public WeatherService weatherService() {
        return Mockito.mock(WeatherService.class);
    }
}
