package com.curve.weather.core;

import io.github.cdimascio.dotenv.Dotenv;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class Config {

    private static Dotenv instance;

    static {
        instance = Dotenv.configure().load();
    }

    private Config() {
    }

    private static String getKeyValue(String key) {
        String value = instance.get(key);

        if (value == null || value.isEmpty()) {
            throw new RuntimeException(String.format("Could not find a value for [%s] key. Please check the .env file.", key));
        }

        return value;
    }

    public static class OpenWeatherMap {

        public static String getUnitFormat() {
            return getKeyValue("OWM_UNIT_FORMAT");
        }

        public static String getApiKey() {
            return getKeyValue("OWM_API_KEY");
        }

        public static String getForecastBasePath() {
            return getKeyValue("OWM_FORECAST_BASE_PATH");
        }

        public static String getBaseUri() {
            return getKeyValue("OWM_BASE_URI");
        }

        public static List<Integer> getCoveredCityIds() {
            return Arrays.stream(getKeyValue("OWM_COVERED_CITY_IDS").split(",")).map(Integer::parseInt)
                    .collect(Collectors.toList());
        }

        // TODO: requires some rethinking, I feel there is more flowy approach to loading city id and names from .env
        public static String getCityName(Integer cityId) {
            return getKeyValue(String.valueOf(cityId));
        }
    }

    public static class Reddit {

        public static String getBaseUri() {
            return getKeyValue("REDDIT_BASE_URI");
        }

        public static String getOAuthBaseUri() {
            return getKeyValue("REDDIT_OAUTH_BASE_URI");
        }

        public static String getSubmitBasePath() {
            return getKeyValue("REDDIT_SUBMIT_BASE_PATH");
        }

        public static String getComposeBasePath() {
            return getKeyValue("REDDIT_COMPOSE_BASE_PATH");
        }

        public static String getAuthorizationBasePath() {
            return getKeyValue("REDDIT_AUTHORIZATION_PATH");
        }

        public static String getClientId() {
            return getKeyValue("REDDIT_CLIENT_ID");
        }

        public static String getClientSecret() {
            return getKeyValue("REDDIT_CLIENT_SECRET");
        }

        public static String getUserAgent() {
            return getKeyValue("REDDIT_CLIENT_USER_AGENT");
        }

        public static String getUsername() {
            return getKeyValue("REDDIT_USERNAME");
        }

        public static String getPassword() {
            return getKeyValue("REDDIT_PASSWORD");
        }

        public static String getTestSubredditName() {
            return getKeyValue("REDDIT_TEST_SUBREDDIT_NAME");
        }

        public static String getCurveRecipientUsername() {
            return getKeyValue("REDDIT_CURVE_RECIPIENT_USERNAME");
        }
    }
}
