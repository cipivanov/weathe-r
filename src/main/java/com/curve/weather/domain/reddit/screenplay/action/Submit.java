package com.curve.weather.domain.reddit.screenplay.action;

import java.util.Collections;

import com.curve.weather.core.screenplay.Action;
import com.curve.weather.core.screenplay.Actor;
import com.curve.weather.domain.reddit.api.adapter.SubmitAdapter;

public class Submit extends Action {

    public Submit() {
        super("Send Submit Request", Collections.emptySet());
    }

    @Override
    public void performAs(final Actor actor) {
        String postUrl = 
            actor
                .ability(SubmitAdapter.class)
                .enabler()
                .create();
        actor.memorizes("url", postUrl);
    }

	public static Submit send() {
		return new Submit();
	}

}