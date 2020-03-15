package com.curve.weather.domain.openweather.api.model;

import com.curve.weather.domain.openweather.api.model.deserializer.ForecastDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.time.LocalDateTime;

@JsonDeserialize(using = ForecastDeserializer.class)
public class Forecast {

    private LocalDateTime dateTime;
    private Double temperature;

    public Forecast() {
    }

    public Forecast(LocalDateTime date, Double temperature) {
        this.dateTime = date;
        this.temperature = temperature;
    }

    public static EmptyForecast empty() {
        return new EmptyForecast();
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDate(LocalDateTime date) {
        this.dateTime = date;
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
