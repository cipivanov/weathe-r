package com.curve.weather.domain.reddit.api.model;

import java.util.List;

public class PrivateMessage {

    private List<String> errors;

    public PrivateMessage(List<String> errors) {
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}