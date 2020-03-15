package com.curve.weather.domain.reddit.screenplay.task;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.curve.weather.core.screenplay.Task;
import com.curve.weather.domain.reddit.screenplay.action.PickSubmitBody;
import com.curve.weather.domain.reddit.screenplay.action.PickSubmitSubreddit;
import com.curve.weather.domain.reddit.screenplay.action.PickSubmitTitle;
import com.curve.weather.domain.reddit.screenplay.action.Submit;

public class SubmitCoveredCitiesHottestWeather extends Task {

	protected SubmitCoveredCitiesHottestWeather() {
		super(
			"Submit Cities Hottest Weather Day Report To Reddit", 
			Stream.of(
				PickSubmitSubreddit.withName("test"), 
				PickSubmitTitle.get(), 
				PickSubmitBody.get(),
				Submit.send()
			).collect(Collectors.toCollection(LinkedHashSet::new))
		);
	}

	public static SubmitCoveredCitiesHottestWeather post() {
		return new SubmitCoveredCitiesHottestWeather();
	}
}
