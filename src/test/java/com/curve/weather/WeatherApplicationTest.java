package com.curve.weather;

import static com.curve.weather.domain.openweather.screenplay.Abilities.getWeatherForecast;
import static com.curve.weather.domain.reddit.screenplay.Abilities.sendRedditPrivateMessage;
import static com.curve.weather.domain.reddit.screenplay.Abilities.submitRedditPost;
import static org.assertj.core.api.Assertions.assertThat;

import com.curve.weather.core.Config.Reddit;
import com.curve.weather.core.screenplay.Actor;
import com.curve.weather.domain.openweather.api.adapter.ForecastAdapter;
import com.curve.weather.domain.openweather.api.model.Forecast;
import com.curve.weather.domain.openweather.screenplay.question.CitiesWeatherForecast;
import com.curve.weather.domain.reddit.api.adapter.ComposeAdapter;
import com.curve.weather.domain.reddit.api.adapter.SubmitAdapter;
import com.curve.weather.domain.reddit.screenplay.task.CurveAccountWeatherPrivateMessage;
import com.curve.weather.domain.reddit.screenplay.task.SubmitCoveredCitiesHottestWeather;

import org.junit.jupiter.api.Test;

public class WeatherApplicationTest {

    @Test
    public void shouldGetForecast() {
        assertThat(new ForecastAdapter().forCity(2643743).getHottestDay()).isNotEqualTo(Forecast.empty());
    }

    @Test
    public void shouldsubmitPost() {
        new SubmitAdapter().subreddit("test").title("test").body("test").create();
    }

    @Test
    public void shouldSendPrivateMessage() {
        assertThat(new ComposeAdapter().to(Reddit.getCurveRecipientUsername()).subject("test").body("test").send().getErrors()).isEmpty();
    }

    @Test
    public void shouldSubmitWeatherForCoveredCitiesAndMessageTestAccount() {
        Actor keanu = Actor.named("Keanu");

        keanu.can(
            submitRedditPost(),
            getWeatherForecast(),
            sendRedditPrivateMessage()
        );

        keanu.attemptsTo(
            CitiesWeatherForecast.get(),
            SubmitCoveredCitiesHottestWeather.post(),
            CurveAccountWeatherPrivateMessage.send()
        );
    }
}
