package ru.aikam.task.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.aikam.task.entity.input.SearchOperation;

/**
 * Класс сериализующий класс SearchOperation в строку формата json
 *
 * @author Kami
 */
public class SearchOperationSerialization {
    /**
     * @param searchOperation класс подлежащий сериализации
     * @return строка формата json
     */
    public String toJson(SearchOperation searchOperation) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        return gson.toJson(searchOperation);
    }
}
