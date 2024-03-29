package com.curve.weather.domain.reddit.screenplay.action;

import com.curve.weather.core.Config.Reddit;
import com.curve.weather.core.screenplay.Action;
import com.curve.weather.core.screenplay.Actor;
import com.curve.weather.core.screenplay.Check;
import com.curve.weather.domain.reddit.api.adapter.ComposeAdapter;

import java.util.Set;
import java.util.function.Consumer;

public class PrivateMessage extends Action {

    private static final String PM_BODY_TEMPLATE = "Please find the weather report post by following the [link](%s).";

    private Consumer<Actor> consumer;

    public PrivateMessage(String name, Consumer<Actor> consumer) {
        super(name);
        this.consumer = consumer;
    }

    @SuppressWarnings("rawtypes")
    public PrivateMessage(String name, Set<Check> checks, Consumer<Actor> consumer) {
        super(name, checks);
        this.consumer = consumer;
    }

    public static PrivateMessage toUser(String user) {
        return new PrivateMessage(
                String.format("Set User To <%s>", user),
                (actor) -> actor.ability(ComposeAdapter.class).enabler().addParameter("to", user)
        );
    }

    // TODO: should not be static all, should allow chaining

    public static PrivateMessage withSubject(String subject) {
        return new PrivateMessage(
                String.format("Set Subject To <%s>", subject),
                (actor) -> actor.ability(ComposeAdapter.class).enabler().addParameter("subject", subject)
        );
    }

    public static PrivateMessage withBody(String body) {
        return new PrivateMessage(
                String.format("Set Body To <%s>", body),
                (actor) -> actor.ability(ComposeAdapter.class).enabler().addParameter("text", body)
        );
    }

    public static PrivateMessage toTestUser() {
        return new PrivateMessage(
                "Set User To Test User",
                (actor) -> actor.ability(ComposeAdapter.class).enabler().addParameter("to", Reddit.getCurveRecipientUsername())
        );
    }

    public static PrivateMessage withWeatherReportSubject() {
        return new PrivateMessage(
                "Set Subject To Weather Report Subject",
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
                (actor) -> actor.memorizes("pm", actor.ability(ComposeAdapter.class).enabler().send())
        );
    }

    private static String getWeatherReportBody(String postLink) {
        return String.format(PM_BODY_TEMPLATE, postLink);
    }

    @Override
    public void performAs(Actor actor) {
        consumer.accept(actor);
    }
}
