package ru.aikam.task.json.output;

import ru.aikam.task.json.Criterion;

import java.util.LinkedList;
import java.util.List;

public class Request {
    private Criterion criterion;
    private List<Result> results = new LinkedList<>();

    public void addResult(Result result) {
        results.add(result);
    }
}
