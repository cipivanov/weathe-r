package com.curve.weather.core.screenplay;

public abstract class Ability<T> {

    private T enabler;
    private String name;

    protected Ability(final String name, final T enabler) {
        this.name = name;
        this.enabler = enabler;
    }

    public String getName() {
        return name;
    }

    public T enabler() {
        return enabler;
    }
}
