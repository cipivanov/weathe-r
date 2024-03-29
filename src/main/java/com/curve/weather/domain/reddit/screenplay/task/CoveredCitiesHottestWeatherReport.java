package com.curve.weather.domain.reddit.screenplay.task;

import com.curve.weather.core.screenplay.Task;
import com.curve.weather.domain.reddit.screenplay.action.Submit;
import com.curve.weather.domain.reddit.screenplay.question.SubmitRequest;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CoveredCitiesHottestWeatherReport extends Task {

    private CoveredCitiesHottestWeatherReport() {
        super(
                "Submit Covered Cities Hottest Weather Day Report To Reddit",
                Stream.of(
                        Submit.toTestSubreddit(),
                        Submit.withWeatherReportTitle(),
                        Submit.withWeatherReportBody(),
                        Submit.create()
                ).collect(Collectors.toCollection(LinkedHashSet::new)),
                Stream.of(
                        SubmitRequest.successful()
                ).collect(Collectors.toCollection(LinkedHashSet::new))
        );
    }

    public static CoveredCitiesHottestWeatherReport submit() {
        return new CoveredCitiesHottestWeatherReport();
    }
}
