package ru.aikam.task.entity.input;

import lombok.Getter;
import lombok.Setter;
import ru.aikam.task.entity.Criterion;

@Getter
@Setter
public final class ProductNameCriterion extends Criterion {
    private final String productName;
    private final Integer minTimes;

    public ProductNameCriterion(String productName, Integer minTimes) {
        this.productName = productName;
        this.minTimes = minTimes;
        super.type = "productName";
    }

    @Override
    public boolean isIncomplete() {
        return productName == null || minTimes == null;
    }
}
