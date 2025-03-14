package com.mdharr.weatherapp.transformer;

import com.mdharr.weatherapp.domain.CityCoordinates;
import com.mdharr.weatherapp.entity.GeocodingCoordinatesEntity;
import org.springframework.stereotype.Service;

@Service
public class GeocodingCoordinatesTransformer {

    public CityCoordinates transformToDomain(final GeocodingCoordinatesEntity entity) {
        return CityCoordinates.builder()
                .latitude(entity.getLatitude())
                .longitude(entity.getLongitude())
                .build();
    }
}
