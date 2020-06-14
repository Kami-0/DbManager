package ru.aikam.task.entity.input;

import lombok.Getter;
import lombok.Setter;
import ru.aikam.task.entity.Criterion;

@Getter
@Setter
public final class LastNameCriterion extends Criterion {
    private final String lastName;

    public LastNameCriterion(String lastName) {
        this.lastName = lastName;
        super.type = "lastName";
    }

    @Override
    public boolean isIncomplete() {
        return lastName == null;
    }
}
