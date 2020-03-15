package com.curve.weather.domain.reddit.screenplay.action;

import java.util.Collections;

import com.curve.weather.core.screenplay.Action;
import com.curve.weather.core.screenplay.Actor;
import com.curve.weather.domain.reddit.api.adapter.SubmitAdapter;

public class PickSubmitSubreddit extends Action {

	private String subreddit;

	private PickSubmitSubreddit(String subreddit) {
		super("Pick Subreddit To Post To", Collections.emptySet());
		this.subreddit = subreddit;
	}

	public static PickSubmitSubreddit withName(String name) {
		return new PickSubmitSubreddit(name);
	}

	@Override
	public void performAs(final Actor actor) {
		actor
			.ability(SubmitAdapter.class)
			.enabler()
			.subreddit(subreddit);
	}

}
