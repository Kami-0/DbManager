package ru.aikam.task.json.output;

import java.util.LinkedList;
import java.util.List;

public class SearchOutputJson {
    private String type;
    private List<Request> requests = new LinkedList<>();

    public void addRequest(Request request) {
        requests.add(request);
    }
}
