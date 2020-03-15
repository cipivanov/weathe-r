package com.curve.weather.core.screenplay;

import java.util.LinkedHashSet;
import java.util.Set;

public abstract class Action implements Performable {

    private String name;
    private Boolean status;
    private Set<Check> checks;

    public Action(String name) {
        this.name = name;
        this.status = true; //TODO: revisit this, default is true if checks have not been performed
        this.checks = new LinkedHashSet<>();
    }

    public Action(String name, Set<Check> checks) {
        this.name = name;
        this.status = true;
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
