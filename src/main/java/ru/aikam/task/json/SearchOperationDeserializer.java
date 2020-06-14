package ru.aikam.task.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.aikam.task.entity.Criterion;
import ru.aikam.task.entity.input.SearchOperation;

/**
 * Класс десериализующий строку формата json в класс SearchOperation
 *
 * @author Daniil Makarov (Kami)
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SearchOperationDeserializer {
    /**
     * Метод десериализующий json в класс
     *
     * @param json строка формата json
     * @return экземпляр класса SearchOperation
     */
    public static SearchOperation fromJson(String json) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Criterion.class, new CriterionDeserializer())
                .create();
        return gson.fromJson(json, SearchOperation.class);
    }
}
