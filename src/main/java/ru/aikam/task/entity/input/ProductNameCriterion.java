package ru.aikam.task.entity.input;

import lombok.Getter;
import lombok.Setter;
import ru.aikam.task.entity.Criterion;

@Getter
@Setter
public final class ProductNameCriterion extends Criterion {
    private final String productName;
    private final int minTimes;

    public ProductNameCriterion(String productName, int minTimes) {
        this.productName = productName;
        this.minTimes = minTimes;
        super.type = "productName";
    }
}
