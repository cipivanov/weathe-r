package com.curve.weather.domain.reddit.adapter;

import com.curve.weather.core.Config.Reddit;
import com.curve.weather.core.api.ApiAdapter;
import com.curve.weather.domain.reddit.model.PrivateMessage;

public class PrivateMessageAdapter extends ApiAdapter {

    AuthorizationAdapter aA = new AuthorizationAdapter();

    public PrivateMessageAdapter() {
        super(Reddit.getOAuthBaseUri(), Reddit.getComposeBasePath());

        addParameter("api_type", "json");
        addHeader("User-Agent", Reddit.getUserAgent());
        addHeader("Authorization", "Bearer ".concat(aA.getAuthorizationToken()));
    }

    public PrivateMessageAdapter to(String userName) {
        addParameter("to", userName);

        return this;
    }

    public PrivateMessageAdapter subject(String aSubject) {
        addParameter("subject", aSubject);

        return this;
    }

    public PrivateMessageAdapter body(String aBody) {
        addParameter("text", aBody);

        return this;
    }

    public PrivateMessage send() {
        // TOOO: figure an asynchronous approach to populating errrors after request is
        // made
        return new PrivateMessage(rs.post().jsonPath().getList("json.errors[0]", String.class));
    }
}
