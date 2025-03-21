package com.mdharr.weatherapp.resource;

import com.mdharr.weatherapp.config.WeatherTestConfig;
import com.mdharr.weatherapp.domain.WeatherRequestDetails;
import com.mdharr.weatherapp.entity.WeatherResponse;
import com.mdharr.weatherapp.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WeatherResource.class)
@Import(WeatherTestConfig.class)
class WeatherResourceTest {

    public static final String CITY = "London";
    public static final String WEATHER = "Sunny";
    public static final String DETAILS = "Very sunny";

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test_should_return_weather_response_success() throws Exception {
        final WeatherRequestDetails requestDetails =
                WeatherRequestDetails.builder()
                        .city(CITY)
                        .build();

        final WeatherResponse weatherResponse =
                WeatherResponse.builder()
                        .weather(WEATHER)
                        .details(DETAILS)
                        .build();

        when(weatherService.getWeather(any(WeatherRequestDetails.class))).thenReturn(weatherResponse);

        mockMvc.perform(get("/api/v1/weather/{city}", CITY)).andDo(print())
                .andExpect(status().isOk());
    }
}