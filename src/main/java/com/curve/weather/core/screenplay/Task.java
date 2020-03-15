package com.curve.weather.core.screenplay;

import java.util.LinkedHashSet;
import java.util.Set;

public abstract class Task implements Performable {

    private String name;
    private Set<Action> actions;
    @SuppressWarnings("rawtypes")
    private Set<Check> checks;
    private Boolean status;

    protected Task(final String name, final Set<Action> actions) {
        this.name = name;
        this.actions = actions;
        this.checks = new LinkedHashSet<>();
    }

    protected Task(final String name, final Set<Action> actions, @SuppressWarnings("rawtypes") Set<Check> checks) {
        this.name = name;
        this.actions = actions;
        this.checks = checks;
    }

    public Set<Action> getActions() {
        return actions;
    }

    @Override
    public void performAs(Actor actor) {
        actions.stream().forEach(action -> action.performAs(actor));
    }

    @Override
    public void checkAs(Actor actor) {
        status = checks.stream().allMatch(check -> check.checkAs(actor).equals(true));
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Boolean getStatus() {
        return status;
    }

    @SuppressWarnings("rawtypes")
    public Set<Check> getChecks() {
        return checks;
    }
}
