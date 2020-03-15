package com.curve.weather.domain.reddit.screenplay.question;

import com.curve.weather.core.screenplay.Actor;
import com.curve.weather.core.screenplay.Check;
import com.curve.weather.domain.reddit.api.model.PrivateMessage;

public class PrivateMessageRequest extends Check<PrivateMessage> {

    protected PrivateMessageRequest() {
        super(
                "Private Message Request Successful",
                (errors) -> errors.getErrors().isEmpty()
        );
    }

    public static PrivateMessageRequest successful() {
        return new PrivateMessageRequest();
    }

    @Override
    public Boolean checkAs(Actor actor) {
        return getResult().test(actor.recalls("pm"));
    }

}
