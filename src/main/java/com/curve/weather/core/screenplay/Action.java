package com.curve.weather.core.screenplay;

import java.util.LinkedHashSet;
import java.util.Set;

public abstract class Action implements Performable {

    private String name;

    @SuppressWarnings("rawtypes")
    private Set<Check> checks;
    private Boolean status;

    public Action(String name) {
        this.name = name;
        this.checks = new LinkedHashSet<>();
    }

    @SuppressWarnings("rawtypes")
    public Action(String name, Set<Check> checks) {
        this.name = name;
        this.checks = checks;
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
