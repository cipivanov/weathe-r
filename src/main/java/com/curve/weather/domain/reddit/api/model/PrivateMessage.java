package com.curve.weather.domain.reddit.api.model;

import java.util.List;

public class PrivateMessage {
    private String body;
    private String subject;
    private String receipient;
    private List<String> errors;

    public PrivateMessage(String body, String subject, String receipient) {
        this.body = body;
        this.subject = subject;
        this.receipient = receipient;
    }

    public PrivateMessage(List<String> errors) {
        this.errors = errors;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getReceipient() {
        return receipient;
    }

    public void setReceipient(String receipient) {
        this.receipient = receipient;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}