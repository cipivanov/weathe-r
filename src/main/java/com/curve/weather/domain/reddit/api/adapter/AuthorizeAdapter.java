package com.curve.weather.domain.reddit.api.adapter;

import com.curve.weather.core.Config.Reddit;
import com.curve.weather.core.api.ApiAdapter;

public class AuthorizeAdapter extends ApiAdapter {

    public AuthorizeAdapter() {
        super(Reddit.getBaseUri(), Reddit.getAuthorizationBasePath());
    }

    public String getAuthorizationToken() {
        addParameter("grant_type", "password");
        addParameter("username", Reddit.getUsername());
        addParameter("password", Reddit.getPassword());
        addHeader("User-Agent", Reddit.getUserAgent());

        addBasicAuthorization(Reddit.getClientId(), Reddit.getClientSecret());

        return rs.log().all().post().jsonPath().getString("access_token");
    }
}
