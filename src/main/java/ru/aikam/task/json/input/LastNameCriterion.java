package ru.aikam.task.json.input;

import lombok.Getter;
import ru.aikam.task.json.Criterion;

@Getter
public final class LastNameCriterion implements Criterion {
    private final String lastName;

    public LastNameCriterion(String lastName) {
        this.lastName = lastName;
    }
}
