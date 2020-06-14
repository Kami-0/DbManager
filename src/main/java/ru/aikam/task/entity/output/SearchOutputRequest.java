package ru.aikam.task.entity.output;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
public class SearchOutputRequest implements SerializableInJson {
    private String type = "search";
    private List<SearchCriterionRequest> result = new LinkedList<>();

    public void addRequest(SearchCriterionRequest searchCriterionRequest) {
        result.add(searchCriterionRequest);
    }

    @Override
    public String toJson() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        return gson.toJson(this);
    }

}
