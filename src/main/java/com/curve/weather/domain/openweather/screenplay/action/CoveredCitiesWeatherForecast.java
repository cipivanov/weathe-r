package com.curve.weather.domain.openweather.screenplay.action;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.curve.weather.core.Config.OpenWeatherMap;
import com.curve.weather.core.screenplay.Action;
import com.curve.weather.core.screenplay.Task;

public class CoveredCitiesWeatherForecast extends Task {

    protected CoveredCitiesWeatherForecast() {
        super(
            "Get Covered Cities Weather Forecast",
            OpenWeatherMap.getCoveredCityIds()
                .stream()
                .flatMap(city -> fanOut(city))
                .collect(Collectors.toCollection(LinkedHashSet::new))
        );
    }

    public static CoveredCitiesWeatherForecast get() {
        return new CoveredCitiesWeatherForecast();
    }

    // TODO: should be extracted to actor as there might be ogher cases where this type of composition is required
    // TODO: 1 item -> multiple actions
    private static Stream<Action> fanOut(Integer city) {
        return Stream.of(WeatherForecast.forCity(city), WeatherForecast.get());
    }
}
