package ru.aikam.task.json.input;

import lombok.Getter;
import ru.aikam.task.json.Criterion;

@Getter
public final class ProductExpensesCriterion implements Criterion {
    private final int minExpenses;
    private final int maxExpenses;

    public ProductExpensesCriterion(int minExpenses, int maxExpenses) {
        this.minExpenses = minExpenses;
        this.maxExpenses = maxExpenses;
    }
}
