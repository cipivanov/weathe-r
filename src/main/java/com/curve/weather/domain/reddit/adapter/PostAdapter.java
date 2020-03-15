package com.curve.weather.domain.reddit.adapter;

import com.curve.weather.core.Config.Reddit;
import com.curve.weather.core.api.ApiAdapter;

public class PostAdapter extends ApiAdapter {

    AuthorizationAdapter aA = new AuthorizationAdapter();

    public PostAdapter() {
        super(Reddit.getOAuthBaseUri(), Reddit.getSubmitBasePath());

        addParameter("kind", "self"); // TODO: refactor to allow for better default parameter handling
        addHeader("User-Agent", Reddit.getUserAgent());
        addHeader("Authorization", "Bearer ".concat(aA.getAuthorizationToken()));
    }

    public boolean submitPost() {
        return Boolean.valueOf(rs.log().all().post().jsonPath().getString("success"));
    }

    public PostAdapter toSubreddit(String subreddit) {
        addParameter("sr", subreddit);

        return this;
    }

    public PostAdapter withTitle(String title) {
        addParameter("title", title);

        return this;
    }

    public PostAdapter withBody(String body) {
        addParameter("text", body);

        return this;
    }
}
