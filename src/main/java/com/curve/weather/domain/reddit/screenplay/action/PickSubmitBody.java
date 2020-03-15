package com.curve.weather.domain.reddit.screenplay.action;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.curve.weather.core.Config.OpenWeatherMap;
import com.curve.weather.core.screenplay.Action;
import com.curve.weather.core.screenplay.Actor;
import com.curve.weather.domain.openweather.api.model.Forecast;
import com.curve.weather.domain.reddit.api.adapter.SubmitAdapter;

public class PickSubmitBody extends Action {

	private final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/YYYY");
	private final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/YYYY HH:mm");

	private final String LINE_SEPARATOR = 
		System.lineSeparator();
	private final String BODY_INTRO = 
		"According the Open Weather Map API 5 day forecast (%s - %s), the hottest date and time in these cities will be as follows:";
	private final String BODY_OUTRO = 
		"Feel free to PM me at /u/weathe_r if there is a mistake.";
	private final String BODY_TABLE_HEADER = 
		"City | Hottest Day Date and Time | Temperature (°C)";
	private final String BODY_TABLE_ROW_ALIGNMENT = 
		":-- | :--: | :--:";
	private final String BODY_TABLE_ROW = 
		"%s | %s | %.1f";

	// not final since I intend to keep it "dynamic", in case the number of cities changes
	private String bodyTable = 
		BODY_TABLE_HEADER
			.concat(LINE_SEPARATOR)
			.concat(BODY_TABLE_ROW_ALIGNMENT)
			.concat(LINE_SEPARATOR);
	
	private PickSubmitBody() {
		super("Generate Post Body", Collections.emptySet());
	}

	public static PickSubmitBody get() {
		return new PickSubmitBody();
	}

	@Override
	public void performAs(Actor actor) {
		Map<Integer, List<Forecast>> forecasts = actor.recalls("forecasts");

		bodyTable += 
			forecasts.entrySet()
				.stream()
				.map(city -> getPopulatedTableRow(city).concat(LINE_SEPARATOR))
				.collect(Collectors.joining());

		actor
			.ability(SubmitAdapter.class)
			.enabler()
			.body(getBody());
	}

	private String getPopulatedTableRow(Map.Entry<Integer, List<Forecast>> city) {
		Forecast hottestForecast = getHottestForecast(city.getValue());
		return String.format(BODY_TABLE_ROW, 
			OpenWeatherMap.getCityName(city.getKey()), 
			hottestForecast.getDateTime().format(DATE_TIME_FORMATTER),
			hottestForecast.getTemperature()
		);
	}

	private Forecast getHottestForecast(List<Forecast> forecasts) {
		return forecasts.stream()
			.max(Comparator.comparing(Forecast::getTemperature))
			.orElseThrow();
	}

	private String getBody() {
		LocalDate date = LocalDate.now();
		String startDate = date.format(DATE_FORMATTER);
		String endDate = date.plusDays(5).format(DATE_FORMATTER);
		return String.format(BODY_INTRO, startDate, endDate)
			.concat(LINE_SEPARATOR)
			.concat(LINE_SEPARATOR)
			.concat(bodyTable)
			.concat(LINE_SEPARATOR)
			.concat(LINE_SEPARATOR)
			.concat(BODY_OUTRO);
	}
}
