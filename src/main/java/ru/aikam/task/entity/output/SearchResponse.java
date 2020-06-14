package ru.aikam.task.entity.output;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.aikam.task.entity.SerializableInJson;

import java.util.LinkedList;
import java.util.List;

/**
 * Класс сущность ответа на запрос от пользователя типа search
 *
 * @author Daniil Makarov (Kami)
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponse implements SerializableInJson {
    private String type = "search";
    private List<SearchCriterion> result = new LinkedList<>();

    public void addRequest(SearchCriterion searchCriterion) {
        result.add(searchCriterion);
    }

    @Override
    public String toJson() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        return gson.toJson(this);
    }

}
