package com.curve.weather.domain.reddit.screenplay.action;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.curve.weather.core.Config.OpenWeatherMap;
import com.curve.weather.core.screenplay.Action;
import com.curve.weather.core.screenplay.Actor;
import com.curve.weather.domain.reddit.api.adapter.SubmitAdapter;

public class PickSubmitTitle extends Action {

	private final String TITLE_TEMPLATE = "[%s] Five Day Period Hottest Day Forecast For ";
	
	private final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-YYYY");

	private PickSubmitTitle() {
		super("Generate Post Title", Collections.emptySet());
	}

	public static PickSubmitTitle get() {
		return new PickSubmitTitle();
	}

	@Override
	public void performAs(Actor actor) {
		List<String> cities = 
			OpenWeatherMap.getCoveredCityIds()
				.stream()
				.map(id -> OpenWeatherMap.getCityName(id))
				.collect(Collectors.toList());

		Integer last = cities.size() - 1;

		String citiesInTitle = String.join(" and ", String.join(", ", cities.subList(0, last)), cities.get(last));

		actor
			.ability(SubmitAdapter.class)
			.enabler()
			.title(getTitle(citiesInTitle));
	}

	private String getTitle(String citiesInTitle) {
		return String.format(TITLE_TEMPLATE, getTitleDate()).concat(citiesInTitle);
	}
	
	private String getTitleDate() {
		return LocalDate.now().format(DATE_FORMATTER);
	}
}
