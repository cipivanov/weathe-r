package com.curve.weather.core.screenplay;

import java.util.function.Predicate;

public abstract class Check<T> {

    private String name;
    private Predicate<T> result;

    protected Check(String name, Predicate<T> result) {
        this.name = name;
        this.result = result;
    }

    public abstract Boolean checkAs(Actor actor);

    public String getName() {
        return name;
    }

    protected Predicate<T> getResult() {
        return result;
    }
}
