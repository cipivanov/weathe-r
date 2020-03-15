package com.curve.weather.core.api;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;
import static io.restassured.config.EncoderConfig.encoderConfig;

import io.restassured.specification.RequestSpecification;

public abstract class ApiAdapter {

    protected RequestSpecification rs;

    protected ApiAdapter(final String baseUri, final String basePath) {
        rs = given().baseUri(baseUri).basePath(basePath);
        rs.config(config().encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)));
    }

    protected void addParameter(final String key, final Object value) {
        rs = rs.param(key, value);
    }

    protected void addHeader(final String key, final Object value) {
        rs = rs.header(key, value);
    }

    protected void addBasicAuthorization(final String userName, final String password) {
        rs = rs.auth().preemptive().basic(userName, password);
    }
}
