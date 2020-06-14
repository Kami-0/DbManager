package ru.aikam.task.entity.input;

import lombok.Getter;
import lombok.Setter;
import ru.aikam.task.entity.Criterion;

@Getter
@Setter
public final class ProductExpensesCriterion extends Criterion {
    private final Integer minExpenses;
    private final Integer maxExpenses;

    public ProductExpensesCriterion(int minExpenses, int maxExpenses) {
        this.minExpenses = minExpenses;
        this.maxExpenses = maxExpenses;
        super.type = "productExpenses";
    }

    @Override
    public boolean isIncomplete() {
        return maxExpenses == null || maxExpenses == null;
    }
}
