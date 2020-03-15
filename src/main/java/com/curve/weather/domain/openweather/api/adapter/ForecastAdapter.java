package com.curve.weather.domain.openweather.api.adapter;

import com.curve.weather.core.Config.OpenWeatherMap;
import com.curve.weather.core.api.ApiAdapter;
import com.curve.weather.domain.openweather.api.model.Forecast;

import java.util.List;

public class ForecastAdapter extends ApiAdapter {

    public ForecastAdapter() {
        super(OpenWeatherMap.getBaseUri(), OpenWeatherMap.getForecastBasePath());
    }

    public ForecastAdapter forCity(Integer id) {
        addParameter("id", id);

        return this;
    }

    public ForecastAdapter forCity(String name) {
        addParameter("q", name);

        return this;
    }

    public List<Forecast> get() {
        addParameter("appid", OpenWeatherMap.getApiKey());
        addParameter("units", OpenWeatherMap.getUnitFormat());

        return rs.get().jsonPath().getList("list", Forecast.class);
    }
}