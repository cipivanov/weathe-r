package com.curve.weather.domain.openweather.screenplay;

import com.curve.weather.domain.openweather.screenplay.action.CoveredCitiesWeatherForecast;
import com.curve.weather.domain.openweather.screenplay.action.WeatherForecast;

public class Tasks {

    public static CoveredCitiesWeatherForecast getCoveredCitiesWeatherForecast() {
        return CoveredCitiesWeatherForecast.get();
    }

    public static WeatherForecast setForecastFor(String city) {
        return WeatherForecast.forCity(city);
    }

    public static WeatherForecast getHottestDayFor(String city) {
        return WeatherForecast.getHottestDayFor(city);
    }

    public static WeatherForecast getColdestDayFor(String city) {
        return WeatherForecast.getColdestDayFor(city);
    }

    public static WeatherForecast showsBothForecasts() {
        return WeatherForecast.printsBothForecasts();
    }
}
