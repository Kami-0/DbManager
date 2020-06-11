package ru.aikam.task.json.input;

import lombok.Getter;
import ru.aikam.task.json.Criterion;

@Getter
public final class BadCustomersCriterion implements Criterion {
    private final int badCustomers;

    public BadCustomersCriterion(int badCustomers) {
        this.badCustomers = badCustomers;
    }
}
