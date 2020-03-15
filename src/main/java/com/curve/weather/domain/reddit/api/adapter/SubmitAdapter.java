package com.curve.weather.domain.reddit.api.adapter;

import com.curve.weather.core.Config.Reddit;
import com.curve.weather.core.api.ApiAdapter;

public class SubmitAdapter extends ApiAdapter {

    AuthorizeAdapter aA = new AuthorizeAdapter();

    public SubmitAdapter() {
        super(Reddit.getOAuthBaseUri(), Reddit.getSubmitBasePath());

        addParameter("kind", "self"); // TODO: refactor to allow for better default parameter handling
        addHeader("User-Agent", Reddit.getUserAgent());
        addParameter("api_type", "json");
        addHeader("Authorization", "Bearer ".concat(aA.getAuthorizationToken()));
    }

    public String create() {
        return rs.post().jsonPath().getString("json.data.url");
    }

    public SubmitAdapter subreddit(String subreddit) {
        addParameter("sr", subreddit);

        return this;
    }

    public SubmitAdapter title(String title) {
        addParameter("title", title);

        return this;
    }

    public SubmitAdapter body(String body) {
        addParameter("text", body);

        return this;
    }
}
