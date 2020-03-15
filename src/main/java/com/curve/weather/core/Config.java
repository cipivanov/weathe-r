package com.curve.weather.core;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import io.github.cdimascio.dotenv.Dotenv;

public final class Config {

    private static Dotenv instance;

    static {
        instance = Dotenv.configure().load();
    }

    private Config() {
    }

    public static class OpenWeatherMap {

        public static String getUnitFormat() {
            return instance.get("OWM_UNIT_FORMAT");
        }

        public static String getApiKey() {
            return instance.get("OWM_API_KEY");
        }

        public static String getForecastBasePath() {
            return instance.get("OWM_FORECAST_BASE_PATH");
        }

        public static String getBaseUri() {
            return instance.get("OWM_BASE_URI");
        }

        public static List<Integer> getCoveredCityIds() {
            return Arrays.stream(instance.get("OWM_COVERED_CITY_IDS").split(",")).map(Integer::parseInt)
                    .collect(Collectors.toList());
        }

        // TODO: requires some rethinking, I feel there is more flowy approach to loading city id and names from .env
        public static String getCityName(Integer cityId) {
            return instance.get(String.valueOf(cityId));
        }
    }

    public static class Reddit {

        public static String getBaseUri() {
            return instance.get("REDDIT_BASE_URI");
        }

        public static String getOAuthBaseUri() {
            return instance.get("REDDIT_OAUTH_BASE_URI");
        }

        public static String getSubmitBasePath() {
            return instance.get("REDDIT_SUBMIT_BASE_PATH");
        }

        public static String getComposeBasePath() {
            return instance.get("REDDIT_COMPOSE_BASE_PATH");
        }

        public static String getAuthorizationBasePath() {
            return instance.get("REDDIT_AUTHORIZATION_PATH");
        }

        public static String getClientId() {
            return instance.get("REDDIT_CLIENT_ID");
        }

        public static String getClientSecret() {
            return instance.get("REDDIT_CLIENT_SECRET");
        }

        public static String getUserAgent() {
            return instance.get("REDDIT_CLIENT_USER_AGENT");
        }

        public static String getUsername() {
            return instance.get("REDDIT_USERNAME");
        }

        public static String getPassword() {
            return instance.get("REDDIT_PASSWORD");
        }

        public static String getTestSubredditName() {
            return instance.get("REDDIT_TEST_SUBREDDIT_NAME");
        }

        public static String getCurveRecipientUsername() {
            return instance.get("REDDIT_CURVE_RECIPIENT_USERNAME");
        }
    }
}
