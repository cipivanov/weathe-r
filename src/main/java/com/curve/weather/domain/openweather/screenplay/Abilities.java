package com.curve.weather.domain.openweather.screenplay;

import com.curve.weather.core.screenplay.Ability;
import com.curve.weather.domain.openweather.api.adapter.ForecastAdapter;

public final class Abilities {

    private Abilities() {
    }

    private static class GetForecast extends Ability<ForecastAdapter> {
        private GetForecast() {
			super("Get Weather Forecast From Open Weather Map", new ForecastAdapter());
        }
    }

    public static GetForecast getWeatherForecast() {
        return new GetForecast();
    }
}
