package com.mdharr.weatherapp.service;

import com.mdharr.weatherapp.domain.CityCoordinates;
import com.mdharr.weatherapp.domain.CityWeather;
import com.mdharr.weatherapp.domain.WeatherRequestDetails;
import com.mdharr.weatherapp.entity.WeatherResponse;
import com.mdharr.weatherapp.provider.GeocodingProvider;
import com.mdharr.weatherapp.provider.WeatherProvider;
import com.mdharr.weatherapp.transformer.GeocodingCoordinatesTransformer;
import com.mdharr.weatherapp.transformer.OpenWeatherTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

    private GeocodingProvider geocodingProvider;
    private GeocodingCoordinatesTransformer geocodingCoordinatesTransformer;
    private WeatherProvider weatherProvider;
    private OpenWeatherTransformer openWeatherTransformer;

    @Autowired
    public WeatherService(final GeocodingProvider geocodingProvider,
                          final GeocodingCoordinatesTransformer geocodingCoordinatesTransformer,
                          final WeatherProvider weatherProvider,
                          final OpenWeatherTransformer openWeatherTransformer) {
        this.geocodingProvider = geocodingProvider;
        this.geocodingCoordinatesTransformer = geocodingCoordinatesTransformer;
        this.weatherProvider = weatherProvider;
        this.openWeatherTransformer = openWeatherTransformer;
    }

    public WeatherResponse getWeather(final WeatherRequestDetails weatherRequestDetails) throws Exception {

        // get latitude and longitude
        final CityCoordinates cityCoordinates = geocodingCoordinatesTransformer
                .transformToDomain(geocodingProvider.getCoordinates(weatherRequestDetails));

        // get weather for geo coordinates
        final CityWeather cityWeather = openWeatherTransformer
                .transformToDomain(weatherProvider.getWeather(cityCoordinates));

        return openWeatherTransformer.transformToEntity(cityWeather);
    }
}
