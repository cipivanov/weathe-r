package com.curve.weather.domain.reddit.screenplay.task;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.curve.weather.core.screenplay.Task;
import com.curve.weather.domain.reddit.screenplay.action.PrivateMessage;
import com.curve.weather.domain.reddit.screenplay.question.PrivateMessageRequest;

public class PrivateMessageToTestAccount extends Task {

	protected PrivateMessageToTestAccount() {
		super(
			"Send Message To Curve Account Regarding Weather Report Post", 
			Stream.of(
				PrivateMessage.toTestUser(),
				PrivateMessage.withWeatherReportSubject(),
				PrivateMessage.withWeatherReportBody(),
				PrivateMessage.send()
			).collect(Collectors.toCollection(LinkedHashSet::new)),
			Stream.of(
				PrivateMessageRequest.successful()
			).collect(Collectors.toCollection(LinkedHashSet::new))
		);
	}

	public static PrivateMessageToTestAccount send() {
		return new PrivateMessageToTestAccount();
	}

}
