package com.mdharr.weatherapp.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class WeatherRequestDetails {
    private String city;
}
