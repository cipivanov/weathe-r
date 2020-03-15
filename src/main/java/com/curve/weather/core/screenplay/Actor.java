package com.curve.weather.core.screenplay;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class Actor {

    private final static Logger LOGGER = Logger.getLogger(Actor.class.getName());

    private final String name;
    private final Map<String, Object> memory;
    @SuppressWarnings("rawtypes") private final Map<Class, Ability> abilities;


    public static Actor named(final String name) {
        return new Actor(name);
    }

    private Actor(final String name) {
        this.name = name;
        this.abilities = new HashMap<>();
        this.memory = new HashMap<>();
    }

    public void can(@SuppressWarnings("rawtypes") final Ability... abilities) {
        Stream.of(abilities).forEach(ability -> add(ability));
    }

	public void attemptsTo(final Performable... performables) {
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

    public <T> void memorizes(String withKey, T value) {
        memory.put(withKey, value);
    }
    
    @SuppressWarnings("unchecked")
	public <T> T recalls(String theKey) {
        return (T) memory.getOrDefault(theKey, null);
    }

    public void shows(String value) {
        LOGGER.info(value);
    }

    @SuppressWarnings("unchecked")
	public <T> Ability<T> ability(Class<T> clazz) {
        if (abilities.containsKey(clazz)) {
            return (Ability<T>) abilities.get(clazz);
        }

        throw new RuntimeException("Actor not capable. Provide proper abilities");
    }
    
    private void add(@SuppressWarnings("rawtypes") final Ability ability) {
        abilities.put(ability.enabler().getClass(), ability);
    }
}
