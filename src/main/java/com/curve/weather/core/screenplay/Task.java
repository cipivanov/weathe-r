package com.curve.weather.core.screenplay;

import java.util.Set;

public abstract class Task implements Performable {

    private String name;
    private Set<Action> actions;

    protected Task(final String name, final Set<Action> actions) {
        this.name = name;
        this.actions = actions;
    }

    public Set<Action> getActions() {
        return actions;
    }

    public void performAs(Actor actor) {
        actions.stream().forEach(action -> action.performAs(actor));
    }

    public String getName() {
        return name;
    }
}
