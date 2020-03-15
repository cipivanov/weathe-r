package com.curve.weather.core.screenplay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashSet;
import java.util.Set;

public abstract class Task implements Performable {

    private static final Logger LOGGER = LoggerFactory.getLogger(Task.class);

    private String name;
    private Set<Action> actions;
    private Set<Check> checks;
    private Boolean status;

    protected Task(final String name, final Set<Action> actions) {
        this.name = name;
        this.actions = actions;
        this.checks = new LinkedHashSet<>();
    }

    protected Task(final String name, final Set<Action> actions, Set<Check> checks) {
        this.name = name;
        this.actions = actions;
        this.checks = checks;
    }

    public Set<Action> getActions() {
        return actions;
    }

    @Override
    public void performAs(Actor actor) {
        actions.forEach(action -> {
            //TODO: needs to be decorated
            LOGGER.info(String.format("Actor [%s] Executing Task [%s] Action [%s]", actor.getName(), name, action.getName()));
            action.performAs(actor);
            LOGGER.info(String.format("Action <%s>", action.getStatus() ? "Executed Successfully" : "Failed"));
        });
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
}
