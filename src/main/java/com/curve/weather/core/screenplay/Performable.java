package com.curve.weather.core.screenplay;

public interface Performable {

    void performAs(Actor actor);

    void checkAs(Actor actor);

    String getName();

    Boolean getStatus();
}
