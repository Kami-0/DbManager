package ru.aikam.task.entity.output;

import ru.aikam.task.entity.Criterion;

import java.util.LinkedList;
import java.util.List;

public class Request {
    private Criterion criterion;
    private List<Result> results = new LinkedList<>();

    public void addResult(Result result) {
        results.add(result);
    }
}
