package com.curve.weather.domain.openweather.screenplay.question;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.curve.weather.core.Config.OpenWeatherMap;
import com.curve.weather.core.screenplay.Actor;
import com.curve.weather.core.screenplay.Task;
import com.curve.weather.domain.openweather.api.adapter.ForecastAdapter;
import com.curve.weather.domain.openweather.api.model.Forecast;

public class CitiesWeatherForecast extends Task {

    protected CitiesWeatherForecast() {
        super("Request Covered Cities Weather Forecast", Collections.emptySet());
    }

    public static CitiesWeatherForecast get() {
        return new CitiesWeatherForecast();
    }

    @Override
    public void performAs(Actor actor) {
        Map<Integer, List<Forecast>> forecasts = new HashMap<>();

        ForecastAdapter fA = actor.ability(ForecastAdapter.class).enabler();

        for (Integer cityId : OpenWeatherMap.getCoveredCityIds()) {
            forecasts.put(cityId, fA.forCity(cityId).get());
        }

        actor.memorizes("forecasts", forecasts);
    }
}
