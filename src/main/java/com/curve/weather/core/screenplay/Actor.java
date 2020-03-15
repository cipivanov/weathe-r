package com.curve.weather.core.screenplay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Actor {

    private static final Logger LOGGER = LoggerFactory.getLogger(Actor.class);

    private final String name;
    private final Map<String, Object> memory;
    private final Map<Class, Ability> abilities;

    private Actor(final String name) {
        this.name = name;
        this.memory = new HashMap<>();
        this.abilities = new HashMap<>();
    }

    public static Actor named(final String name) {
        return new Actor(name);
    }

    public void can(final Ability... abilities) {
        Stream.of(abilities).forEach(this::add);
    }

    @SuppressWarnings("unchecked")
    public <T> Ability<T> ability(Class<T> clazz) {
        if (abilities.containsKey(clazz)) {
            return (Ability<T>) abilities.get(clazz);
        }

        throw new RuntimeException("Actor not capable. Provide proper abilities.");
    }

    public void attemptsTo(final Performable... performables) {
        //TODO: decorate
        for (Performable performable : performables) {
            LOGGER.info(String.format("Actor [%s] Executing Performable [%s]", name, performable.getName()));

            performable.performAs(this);
            performable.checkAs(this);

            LOGGER.info(String.format("Performable [%s]", performable.getStatus() ? "Executed Successfully" : "Failed"));

            if (!performable.getStatus()) {
                throw new RuntimeException("Performable Check Failed");
            }
        }
    }

    public void shows(String value) {
        LOGGER.info(String.format("Actor [%s] shows [%s]", name, value));
    }

    public <T> void memorizes(String withKey, T value) {
        memory.put(withKey, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T recalls(String theKey) {
        return (T) memory.getOrDefault(theKey, null);
    }

    public String getName() {
        return name;
    }

    private void add(@SuppressWarnings("rawtypes") final Ability ability) {
        abilities.put(ability.enabler().getClass(), ability);
    }
}
