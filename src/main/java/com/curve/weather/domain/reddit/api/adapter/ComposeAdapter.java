package com.curve.weather.domain.reddit.api.adapter;

import com.curve.weather.core.Config.Reddit;
import com.curve.weather.core.api.ApiAdapter;
import com.curve.weather.domain.reddit.api.model.PrivateMessage;

public class ComposeAdapter extends ApiAdapter {

    AuthorizeAdapter aA = new AuthorizeAdapter();

    public ComposeAdapter() {
        super(Reddit.getOAuthBaseUri(), Reddit.getComposeBasePath());

        addParameter("api_type", "json");
        addHeader("User-Agent", Reddit.getUserAgent());
        addHeader("Authorization", "Bearer ".concat(aA.getAuthorizationToken()));
    }

    public ComposeAdapter to(String userName) {
        addParameter("to", userName);

        return this;
    }

    public ComposeAdapter subject(String aSubject) {
        addParameter("subject", aSubject);

        return this;
    }

    public ComposeAdapter body(String aBody) {
        addParameter("text", aBody);

        return this;
    }

    public PrivateMessage send() {
        // TOOO: figure an asynchronous approach to populating errrors after request is made
        return new PrivateMessage(rs.post().jsonPath().getList("json.errors[0]", String.class));
    }
}
