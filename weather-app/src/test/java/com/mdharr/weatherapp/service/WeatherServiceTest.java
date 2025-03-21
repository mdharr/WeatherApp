package com.mdharr.weatherapp.service;

import com.mdharr.weatherapp.domain.CityCoordinates;
import com.mdharr.weatherapp.domain.WeatherRequestDetails;
import com.mdharr.weatherapp.entity.GeocodingCoordinatesEntity;
import com.mdharr.weatherapp.entity.OpenWeatherResponseEntity;
import com.mdharr.weatherapp.entity.WeatherEntity;
import com.mdharr.weatherapp.entity.WeatherResponse;
import com.mdharr.weatherapp.provider.GeocodingProvider;
import com.mdharr.weatherapp.provider.WeatherProvider;
import com.mdharr.weatherapp.transformer.GeocodingCoordinatesTransformer;
import com.mdharr.weatherapp.transformer.OpenWeatherTransformer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {

    public static final String CITY = "London";
    public static final String LATITUDE = "11.98";
    public static final String LONGITUDE = "34.89";
    public static final String WEATHER = "Rain";
    public static final String DETAILS = "A lot of rain";

    @Mock
    private GeocodingProvider geocodingProvider;

    @Mock
    private WeatherProvider weatherProvider;

    @Mock
    private GeocodingCoordinatesTransformer geocodingCoordinatesTransformer;

    @Mock
    private OpenWeatherTransformer openWeatherTransformer;

    @InjectMocks
    private WeatherService weatherService;

    @Test
    public void test_should_return_weather_response() throws Exception {
        final WeatherRequestDetails requestDetails =
                WeatherRequestDetails.builder()
                        .city(CITY)
                        .build();

        mockGeocodingProvider(requestDetails);
        mockGeocodingCoordinatesTransformer();
        mockWeatherProvider();
        mockOpenWeatherTransformer();

        final WeatherResponse weatherResponse = weatherService.getWeather(requestDetails);

        assertAll("Should return city weather response",
                () -> assertEquals(WEATHER, weatherResponse.getWeather()),
                () -> assertEquals(DETAILS, weatherResponse.getDetails()));
    }

    private void mockOpenWeatherTransformer() {
        final WeatherResponse weatherResponse =
                WeatherResponse.builder()
                        .weather(WEATHER)
                        .details(DETAILS)
                        .build();

        when(openWeatherTransformer.transformToEntity(any())).thenReturn(weatherResponse);
    }

    private void mockWeatherProvider() throws Exception {
        final WeatherEntity weather = WeatherEntity.builder()
                .main(WEATHER)
                .description(DETAILS)
                .build();

        final WeatherEntity[] weatherEntities = {weather};
        final OpenWeatherResponseEntity entity = OpenWeatherResponseEntity.builder()
                .weather(weatherEntities)
                .build();

        when(weatherProvider.getWeather(any())).thenReturn(entity);
    }

    private void mockGeocodingCoordinatesTransformer() {
        final CityCoordinates cityCoordinates = CityCoordinates.builder()
                .longitude(LONGITUDE)
                .latitude(LATITUDE)
                .build();

        when(geocodingCoordinatesTransformer.transformToDomain(any())).thenReturn(cityCoordinates);
    }

    private void mockGeocodingProvider(final WeatherRequestDetails requestDetails) throws Exception {
        final GeocodingCoordinatesEntity entity =
                GeocodingCoordinatesEntity.builder()
                        .latitude(LATITUDE)
                        .longitude(LONGITUDE)
                        .build();

        when(geocodingProvider.getCoordinates(requestDetails)).thenReturn(entity);
    }
}