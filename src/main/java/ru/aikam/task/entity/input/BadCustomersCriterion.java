package ru.aikam.task.entity.input;

import lombok.Getter;
import lombok.Setter;
import ru.aikam.task.entity.Criterion;

@Getter
@Setter
public final class BadCustomersCriterion extends Criterion {
    private final int badCustomers;

    public BadCustomersCriterion(int badCustomers) {
        this.badCustomers = badCustomers;
        super.type = "badCustomers";
    }
}
