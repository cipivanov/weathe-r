package com.curve.weather.domain.reddit.screenplay.question;

import com.curve.weather.core.screenplay.Actor;
import com.curve.weather.core.screenplay.Check;

public class SubmitRequest extends Check<String> {

	protected SubmitRequest() {
		super("Submit Request Successful", (url) -> url != null && !url.isEmpty());
	}

	public static SubmitRequest successful() {
		return new SubmitRequest();
	}

	@Override
	public Boolean checkAs(Actor actor) {
		return getResult().test(actor.recalls("url"));
	}

}
