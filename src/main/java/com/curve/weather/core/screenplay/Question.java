package com.curve.weather.core.screenplay;

public abstract class Question<T> {

    private String name;

    public abstract T askAs(Actor actor);
    public abstract void checkAs(Actor actor);

    protected Question(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
