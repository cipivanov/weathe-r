package com.curve.weather.domain.reddit.screenplay;

import com.curve.weather.domain.reddit.screenplay.task.CoveredCitiesHottestWeatherReport;
import com.curve.weather.domain.reddit.screenplay.task.PrivateMessageToTestAccount;

public final class Tasks {

    private Tasks() {
    }

    public static CoveredCitiesHottestWeatherReport submitCoveredCitiesHottestWeatherReport() {
        return CoveredCitiesHottestWeatherReport.submit();
    }

    public static PrivateMessageToTestAccount sendPrivateMessageToTestAccount() {
        return PrivateMessageToTestAccount.send();
    }
}
