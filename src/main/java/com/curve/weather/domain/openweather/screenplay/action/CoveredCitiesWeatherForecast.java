package com.curve.weather.domain.openweather.screenplay.action;

import com.curve.weather.core.Config.OpenWeatherMap;
import com.curve.weather.core.screenplay.Action;
import com.curve.weather.core.screenplay.Task;
import com.curve.weather.domain.openweather.screenplay.check.CoveredCitiesForecast;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CoveredCitiesWeatherForecast extends Task {

    private CoveredCitiesWeatherForecast() {
        super(
                "Get Covered Cities Weather Forecast",
                OpenWeatherMap.getCoveredCityIds()
                        .stream()
                        .flatMap(CoveredCitiesWeatherForecast::fanOut)
                        .collect(Collectors.toCollection(LinkedHashSet::new)),
                Stream.of(CoveredCitiesForecast.isPresent()).collect(Collectors.toSet())
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
