package com.curve.weather.domain.openweather.screenplay.check;

import com.curve.weather.core.screenplay.Actor;
import com.curve.weather.core.screenplay.Check;
import com.curve.weather.domain.openweather.api.model.Forecast;

import java.util.List;
import java.util.Map;

public class CoveredCitiesForecast extends Check<Map<Integer, List<Forecast>>> {

    private CoveredCitiesForecast() {
        super(
                "Covered Cities Forecast Is Present",
                (forecasts) -> forecasts.entrySet().size() == 5 &&
                        forecasts.entrySet().stream().allMatch(forecast -> forecast.getValue().size() == 40)
        );
    }

    public static CoveredCitiesForecast isPresent() {
        return new CoveredCitiesForecast();
    }

    @Override
    public Boolean checkAs(Actor actor) {
        return getResult().test(actor.recalls("forecasts"));
    }
}
