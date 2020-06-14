package ru.aikam.task.entity.input;

import lombok.Getter;
import lombok.Setter;
import ru.aikam.task.entity.Criterion;

@Getter
@Setter
public final class BadCustomersCriterion extends Criterion {
    private final Integer badCustomers;

    public BadCustomersCriterion(int badCustomers) {
        this.badCustomers = badCustomers;
        super.type = "badCustomers";
    }

    @Override
    public boolean isIncomplete() {
        return badCustomers == null;
    }
}
