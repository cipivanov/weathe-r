package com.curve.weather.domain.openweather.screenplay.action;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import com.curve.weather.core.screenplay.Action;
import com.curve.weather.core.screenplay.Actor;
import com.curve.weather.domain.openweather.api.adapter.ForecastAdapter;
import com.curve.weather.domain.openweather.api.model.Forecast;

public class WeatherForecast extends Action {

    private Consumer<Actor> consumer;

    public WeatherForecast(String name, Consumer<Actor> consumer) {
        super(name);
        this.consumer = consumer;
    }
    
    public WeatherForecast(String name, Consumer<Actor> consumer, Integer city) {
        super(name);
        this.consumer = consumer;
	}

	public static WeatherForecast forCity(Integer id) {
        return new WeatherForecast(
            "Set Weather Forecast City ID",
            (actor) -> {
                actor.ability(ForecastAdapter.class).enabler().forCity(id);
                actor.memorizes("id", id);
            }
        );
    }

    public static WeatherForecast forCity(String name) {
        return new WeatherForecast(
            "Set Weather Forecast City Name",
            (actor) -> {
                actor.ability(ForecastAdapter.class).enabler().forCity(name);
            }
        );
    }

    // TODO: think about making this non-static and allow chaining for increased safety and ease of use

    public static WeatherForecast get() {
        return new WeatherForecast(
            "Get Weather Forecast",
            (actor) -> memorizeForecast(actor, actor.ability(ForecastAdapter.class).enabler().get())
        );
    }

    public static WeatherForecast getHottestDayFor(String city) {
        return new WeatherForecast(
            "Get Hotted Day Forecast",
            (actor) -> actor.memorizes("hottest", 
                actor.ability(ForecastAdapter.class).enabler()
                    .forCity(city)
                    .get()
                    .stream()
                    .max(Comparator.comparing(Forecast::getTemperature))
                    .get()
                    .getTemperature()
                    .toString()
            )
        );
    }

    public static WeatherForecast getColdestDayFor(String city) {
        return new WeatherForecast(
            "Get Coldest Day Forecast",
            (actor) -> actor.memorizes("coldest", 
                actor.ability(ForecastAdapter.class).enabler()
                    .forCity(city)
                    .get()
                    .stream()
                    .min(Comparator.comparing(Forecast::getTemperature))
                    .get()
                    .getTemperature()
                    .toString()
            )
        );
    }

    public static WeatherForecast printsBothForecasts() {
        return new WeatherForecast(
            "Get Coldest Day Forecast",
            (actor) -> {
                actor.shows(actor.recalls("hottest").toString().concat(" - is the hottest temperature"));
                actor.shows(actor.recalls("coldest").toString().concat(" - is the coldest temperature"));
            }
        );
	}

    @Override
    public void performAs(Actor actor) {
        consumer.accept(actor);
    }

    // TODO: extract the logic to the actor class and generalize

    private static void memorizeForecast(Actor actor, List<Forecast> forecasts) {
        if (actor.recalls("forecasts") == null) {
            actor.memorizes("forecasts", new HashMap<Integer, List<Forecast>>());
        }

        Map<Integer, List<Forecast>> memorizedForests = actor.recalls("forecasts");

        memorizedForests.put(actor.recalls("id"), forecasts);
    }
}
