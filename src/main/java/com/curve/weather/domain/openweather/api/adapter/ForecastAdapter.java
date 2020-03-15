package com.curve.weather.domain.openweather.api.adapter;

import java.util.Comparator;
import java.util.List;

import com.curve.weather.core.Config.OpenWeatherMap;
import com.curve.weather.core.api.ApiAdapter;
import com.curve.weather.domain.openweather.api.model.Forecast;

public class ForecastAdapter extends ApiAdapter {

    public ForecastAdapter() {
        super(OpenWeatherMap.getBaseUri(), OpenWeatherMap.getForecastBasePath());
    }

    public ForecastAdapter forCity(Integer id) {
        addParameter("id", id);

        return this;
    }

    public List<Forecast> get() {
        addParameter("appid", OpenWeatherMap.getApiKey()); // TODO: review the approach of adding the key right before making the call
        addParameter("units", OpenWeatherMap.getUnitFormat());

        return rs.get().jsonPath().getList("list", Forecast.class);
    }

    // TODO: consider scenarios where multiple days have qualify for hottest and
    // coldest days

    public Forecast getHottestDay() {
        return get().stream().max(Comparator.comparing(Forecast::getTemperature)).orElse(Forecast.empty());
    }

    public Forecast getColdestDay() {
        return get().stream().min(Comparator.comparing(Forecast::getTemperature)).orElse(Forecast.empty());
    }
}