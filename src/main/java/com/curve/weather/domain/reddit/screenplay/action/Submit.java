package com.curve.weather.domain.reddit.screenplay.action;

import com.curve.weather.core.Config.OpenWeatherMap;
import com.curve.weather.core.Config.Reddit;
import com.curve.weather.core.screenplay.Action;
import com.curve.weather.core.screenplay.Actor;
import com.curve.weather.core.screenplay.Check;
import com.curve.weather.domain.openweather.api.model.Forecast;
import com.curve.weather.domain.reddit.api.adapter.SubmitAdapter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Submit extends Action {

    private static final String TITLE_TEMPLATE = "[%s] Five Day Period Hottest Day Forecast For ";

    // most of these constant will end up in a template file (except the date time ones)
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/YYYY");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/YYYY HH:mm");
    private static final String LINE_SEPARATOR =
            System.lineSeparator();
    private static final String BODY_INTRO =
            "According the Open Weather Map API 5 day forecast (%s - %s), the hottest date and time in these cities will be as follows:";
    private static final String BODY_OUTRO =
            "Feel free to PM me at /u/weathe_r if there is a mistake.";
    private static final String BODY_TABLE_HEADER =
            "City | Hottest Day Date and Time | Temperature (°C)";
    private static final String BODY_TABLE_ROW_ALIGNMENT =
            ":-- | :--: | :--:";
    private static final String BODY_TABLE_ROW =
            "%s | %s | %.1f";
    private Consumer<Actor> consumer;

    private Submit(String name, Consumer<Actor> consumer) {
        super(name);
        this.consumer = consumer;
    }

    @SuppressWarnings("rawtypes")
    private Submit(String name, Set<Check> checks) {
        super(name, checks);
    }

    public static Submit toSubreddit(String subreddit) {
        return new Submit(
                String.format("Set Post Subreddit To <%s>", subreddit),
                (actor) -> actor.ability(SubmitAdapter.class).enabler().addParameter("sr", subreddit)
        );
    }

    public static Submit withTitle(String title) {
        return new Submit(
                String.format("Set Post Title To <%s>", title),
                (actor) -> actor.ability(SubmitAdapter.class).enabler().addParameter("title", title)
        );
    }

    public static Submit withBody(String body) {
        return new Submit(
                String.format("Set Post Body To <%s>", body),
                (actor) -> actor.ability(SubmitAdapter.class).enabler().addParameter("text", body)
        );
    }

    public static Submit toTestSubreddit() {
        return toSubreddit(Reddit.getTestSubredditName());
    }

    public static Submit withWeatherReportTitle() {
        return withTitle(getWeatherReportSubmitTitle());
    }

    public static Submit withWeatherReportBody() {
        return new Submit(
                "Get Weather Report Post Body",
                (actor) -> actor.ability(SubmitAdapter.class).enabler()
                        .addParameter("text", getSubmitBody(actor.recalls("forecasts")))
        );
    }

    public static Submit create() {
        return new Submit(
                "Create Post",
                (actor) -> actor.memorizes("url", actor.ability(SubmitAdapter.class).enabler().create())
        );
    }

    private static String getWeatherReportSubmitTitle() {
        List<String> cities =
                OpenWeatherMap.getCoveredCityIds()
                        .stream()
                        .map(id -> OpenWeatherMap.getCityName(id))
                        .collect(Collectors.toList());

        Integer last = cities.size() - 1;

        return getTitle(String.join(" and ", String.join(", ", cities.subList(0, last)), cities.get(last)));
    }

    private static String getWeatherReportSubmitBody(Map<Integer, List<Forecast>> forecasts) {
        return BODY_TABLE_HEADER
                .concat(LINE_SEPARATOR)
                .concat(BODY_TABLE_ROW_ALIGNMENT)
                .concat(LINE_SEPARATOR)
                .concat(forecasts.entrySet()
                        .stream()
                        .map(city -> getPopulatedTableRow(city).concat(LINE_SEPARATOR))
                        .collect(Collectors.joining()
                        )
                );
    }

    private static String getPopulatedTableRow(Map.Entry<Integer, List<Forecast>> city) {
        Forecast hottestForecast = getHottestForecast(city.getValue());
        return String.format(BODY_TABLE_ROW,
                OpenWeatherMap.getCityName(city.getKey()),
                hottestForecast.getDateTime().format(DATE_TIME_FORMATTER),
                hottestForecast.getTemperature()
        );
    }

    private static Forecast getHottestForecast(List<Forecast> forecasts) {
        return forecasts.stream()
                .max(Comparator.comparing(Forecast::getTemperature))
                .orElseThrow();
    }

    private static String getSubmitBody(Map<Integer, List<Forecast>> forecasts) {
        LocalDate date = LocalDate.now();
        String startDate = date.format(DATE_FORMATTER);
        String endDate = date.plusDays(5).format(DATE_FORMATTER);

        return String.format(BODY_INTRO, startDate, endDate)
                .concat(LINE_SEPARATOR)
                .concat(LINE_SEPARATOR)
                .concat(getWeatherReportSubmitBody(forecasts))
                .concat(LINE_SEPARATOR)
                .concat(LINE_SEPARATOR)
                .concat(BODY_OUTRO);
    }

    private static String getTitle(String citiesInTitle) {
        return String.format(TITLE_TEMPLATE, getTitleDate()).concat(citiesInTitle);
    }

    private static String getTitleDate() {
        return LocalDate.now().format(DATE_FORMATTER);
    }

    @Override
    public void performAs(final Actor actor) {
        consumer.accept(actor);
    }
}
