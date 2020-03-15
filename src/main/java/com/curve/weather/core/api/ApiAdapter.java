package com.curve.weather.core.api;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;
import static io.restassured.config.EncoderConfig.encoderConfig;
import static io.restassured.config.ParamConfig.paramConfig;

import io.restassured.specification.RequestSpecification;

public abstract class ApiAdapter {

    protected RequestSpecification rs;

    protected ApiAdapter(final String baseUri, final String basePath) {
        rs = given().baseUri(baseUri).basePath(basePath);
        rs.config(
            config()
                .encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))
                .paramConfig(paramConfig().replaceAllParameters())
        );
    }

    public void addParameter(final String key, final Object value) {
        rs = rs.queryParam(key, value);
    }

    public void addHeader(final String key, final Object value) {
        rs = rs.header(key, value);
    }

    public void addBasicAuthorization(final String userName, final String password) {
        rs = rs.auth().preemptive().basic(userName, password);
    }
}
