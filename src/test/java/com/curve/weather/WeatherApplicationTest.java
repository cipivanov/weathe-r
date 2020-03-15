package com.curve.weather;

import static org.assertj.core.api.Assertions.assertThat;

import com.curve.weather.core.Config.Reddit;
import com.curve.weather.domain.openweather.api.adapter.ForecastAdapter;
import com.curve.weather.domain.openweather.api.model.Forecast;
import com.curve.weather.domain.reddit.adapter.PostAdapter;
import com.curve.weather.domain.reddit.adapter.PrivateMessageAdapter;

import org.junit.jupiter.api.Test;

public class WeatherApplicationTest {

    @Test
    public void shouldGetForecast() {
        assertThat(new ForecastAdapter().forCity(2643743).getHottestDay()).isNotEqualTo(Forecast.empty());
    }

    @Test
    public void shouldsubmitPost() {
        assertThat(new PostAdapter().toSubreddit("test").withTitle("test").withBody("test").submitPost()).isTrue();
    }

    @Test
    public void shouldSendPrivateMessage() {
        assertThat(new PrivateMessageAdapter().to(Reddit.getCurveRecipientUsername()).subject("test").body("test").send().getErrors()).isEmpty();
    }
}
