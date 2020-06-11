package ru.aikam.task.json.input;

import lombok.Getter;
import lombok.Setter;
import ru.aikam.task.json.Criterion;

@Getter
@Setter
public final class LastNameCriterion extends Criterion {
    private final String lastName;

    public LastNameCriterion(String lastName) {
        this.lastName = lastName;
        super.type = "lastName";
    }
}
