package ru.aikam.task.json.input;

import lombok.Getter;
import lombok.Setter;
import ru.aikam.task.json.Criterion;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public final class SearchOperation {
    private List<Criterion> criteria = new LinkedList<>();

    public void addCriterion(Criterion criterion) {
        criteria.add(criterion);
    }
}
