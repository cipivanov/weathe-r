package com.curve.weather.core.screenplay;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Actor {

    private final String name;
    private final Map<Class, Ability> abilities;
    private final Map<String, Object> memory;

    public static Actor named(final String name) {
        return new Actor(name);
    }

    public void can(final Ability... abilities) {
        Stream.of(abilities).forEach(ability -> add(ability));
    }

    public String getName() {
        return name;
    }

    // private

    private Actor(final String name) {
        this.name = name;
        this.abilities = new HashMap<>();
        this.memory = new HashMap<>();
    }

    private void add(final Ability ability) {
        abilities.put(ability.enabler().getClass(), ability);
    }

	public void attemptsTo(final Performable... performables) {
        for (Performable performable : performables) {
            if (performable instanceof Action) {
                Action a = (Action) performable; // :'( MFW no pattern matching
                a.performAs(this);
                a.checkAs(this);
            } else {
                performable.performAs(this);
            }
        }
    }

    public <T> void memorizes(String withKey, T value) {
        memory.put(withKey, value);
    }
    
	public <T> T recalls(String theKey) {
        return (T) memory.get(theKey);
    }

	public <T> Ability<T> ability(Class<T> clazz) {
        if (abilities.containsKey(clazz)) {
            return (Ability<T>) abilities.get(clazz);
        }

        throw new RuntimeException("Actor not capable. Provide proper abilities");
	}
}
