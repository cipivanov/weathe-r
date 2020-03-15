package com.curve.weather.domain.reddit.screenplay.action;

import java.util.Set;
import java.util.function.Consumer;

import com.curve.weather.core.Config.Reddit;
import com.curve.weather.core.screenplay.Action;
import com.curve.weather.core.screenplay.Actor;
import com.curve.weather.core.screenplay.Question;
import com.curve.weather.domain.reddit.api.adapter.ComposeAdapter;

public class PrivateMessage extends Action {

    private static final String PM_BODY_TEMPLATE = "Please find the weather report post by following the [link](%s).";

    private Consumer<Actor> consumer;

    public PrivateMessage(String name, Consumer<Actor> consumer) {
        super(name);
        this.consumer = consumer;
    }

    public PrivateMessage(String name, Set<Question> checks, Consumer<Actor> consumer) {
        super(name, checks);
        this.consumer = consumer;
    }

    @Override
    public void performAs(Actor actor) {
        consumer.accept(actor);
    }

	public static PrivateMessage toUser(String user) {
        return new PrivateMessage(
            "Set User",
            (actor) -> actor.ability(ComposeAdapter.class).enabler().addParameter("to", user)
        );
	}

	public static PrivateMessage withSubject(String subject) {
        return new PrivateMessage(
            "Set Subject",
            (actor) -> actor.ability(ComposeAdapter.class).enabler().addParameter("subject", subject)
        );
	}

	public static PrivateMessage withBody(String body) {
        return new PrivateMessage(
            "Set Body",
            (actor) -> actor.ability(ComposeAdapter.class).enabler().addParameter("text", body)
        );
    }

    public static PrivateMessage toTestUser() {
        return new PrivateMessage(
            "Set User", 
            (actor) -> actor.ability(ComposeAdapter.class).enabler().addParameter("to", Reddit.getCurveRecipientUsername())
        );
	}

	public static PrivateMessage withWeatherReportSubject() {
        return new PrivateMessage(
            "Set Subject", 
            (actor) -> actor.ability(ComposeAdapter.class).enabler().addParameter("subject", "New Weather Report")
        );
	}

	public static PrivateMessage withWeatherReportBody() {
        return new PrivateMessage(
            "Set Body", 
            (actor) -> actor.ability(ComposeAdapter.class).enabler().addParameter("text", getWeatherReportBody(actor.recalls("url")))
        );
    }

	public static PrivateMessage send() {
        return new PrivateMessage(
            "Send Message", 
            (actor) -> actor.ability(ComposeAdapter.class).enabler().send()
        );
    }

    private static String getWeatherReportBody(String postLink) {
        return String.format(PM_BODY_TEMPLATE, postLink);
    }
}
