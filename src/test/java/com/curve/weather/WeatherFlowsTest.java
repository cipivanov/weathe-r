package com.curve.weather;

import com.curve.weather.core.screenplay.Actor;
import org.junit.jupiter.api.Test;

import static com.curve.weather.domain.openweather.screenplay.Abilities.getWeatherForecast;
import static com.curve.weather.domain.openweather.screenplay.Tasks.*;
import static com.curve.weather.domain.reddit.screenplay.Abilities.sendRedditPrivateMessage;
import static com.curve.weather.domain.reddit.screenplay.Abilities.submitRedditPost;
import static com.curve.weather.domain.reddit.screenplay.Tasks.sendPrivateMessageToTestAccount;
import static com.curve.weather.domain.reddit.screenplay.Tasks.submitCoveredCitiesHottestWeatherReport;

public class WeatherFlowsTest {

    @Test
    public void shouldSubmitWeatherForCoveredCitiesAndMessageTestAccount() {
        Actor keanu = Actor.named("Keanu");

        // masked setup
        keanu.can(
                getWeatherForecast(),
                submitRedditPost(),
                sendRedditPrivateMessage()
        );

        // actual testing
        keanu.attemptsTo(
                getCoveredCitiesWeatherForecast(),
                submitCoveredCitiesHottestWeatherReport(),
                sendPrivateMessageToTestAccount()
        );
    }

    @Test
    public void shouldGetMinimumAndMaximumScenarionInLosAngeles() {
        Actor keanu = Actor.named("Keanu");

        keanu.can(
                getWeatherForecast()
        );

        keanu.attemptsTo(
                getHottestDayFor("Los Angeles"),
                getColdestDayFor("Los Angeles"),
                // for historic data it would have been possible to hardcode the expected values, since they do not change
                // because we are talking forecasts (future data) there is no simple way to atomically validate it, just print it
                showBothForecasts()
        );
    }
}
