package com.curve.weather.domain.reddit.screenplay;

import com.curve.weather.core.screenplay.Ability;
import com.curve.weather.domain.reddit.api.adapter.ComposeAdapter;
import com.curve.weather.domain.reddit.api.adapter.SubmitAdapter;

public class Abilities {

    private static class Submit extends Ability<SubmitAdapter> {
        private Submit() {
			super("Submit A Reddit Post", new SubmitAdapter());
        }
    }

    private static class SendPrivateMessage extends Ability<ComposeAdapter> {
        private SendPrivateMessage() {
            super("Send Reddit Private Message", new ComposeAdapter());
        }
    }

    public static Submit submitRedditPost() {
        return new Submit();
    }

    public static SendPrivateMessage sendRedditPrivateMessage() {
        return new SendPrivateMessage();
    }
}
