package com.curve.weather.core.screenplay;

import java.util.LinkedHashSet;
import java.util.Set;

public abstract class Action implements Performable {

    private String name;
    private Set<Question> questions;

    public Action(String name) {
        this.name = name;
    }

    public Action(String name, Set<Question> checks) {
        this.name = name;
        this.questions = new LinkedHashSet<>();
    }

    public void checkAs(Actor actor) {
        questions.stream().forEach(question -> question.checkAs(actor));
    }

	public String getName() {
		return name;
    }

	public Set<Question> getQuestions() {
		return questions;
	}
}
