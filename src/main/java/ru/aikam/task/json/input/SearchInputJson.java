package ru.aikam.task.json.input;

import lombok.Getter;
import ru.aikam.task.json.Criterion;

import java.util.LinkedList;
import java.util.List;

public final class SearchInputJson {
    @Getter
    private List<Criterion> criteria = new LinkedList<>();

    public void addCriterion(Criterion criterion) {
        criteria.add(criterion);
    }
}
