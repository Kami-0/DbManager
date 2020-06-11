package ru.aikam.task.json.input;

import lombok.Getter;
import ru.aikam.task.json.Criterion;

@Getter
public final class ProductNameCriterion implements Criterion {
    private final String productName;
    private final int minTimes;


    public ProductNameCriterion(String productName, int minTimes) {
        this.productName = productName;
        this.minTimes = minTimes;
    }
}
