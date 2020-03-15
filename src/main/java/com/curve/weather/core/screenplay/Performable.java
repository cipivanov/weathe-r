package com.curve.weather.core.screenplay;

public interface Performable {

    public void performAs(Actor actor);

    public void checkAs(Actor actor);

    public String getName();

    public Boolean getStatus();
}
