package com.curve.weather.domain.openweather.api.model;

import java.time.LocalDateTime;

import com.curve.weather.domain.openweather.api.model.deserializer.ForecastDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = ForecastDeserializer.class)
public class Forecast {

    private LocalDateTime date;
    private Double temperature;

    public Forecast() {
    }

    public Forecast(LocalDateTime date, Double temperature) {
        this.date = date;
        this.temperature = temperature;
    }

    public static EmptyForecast empty() {
        return new EmptyForecast();
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    private static class EmptyForecast extends Forecast {
    }
}
