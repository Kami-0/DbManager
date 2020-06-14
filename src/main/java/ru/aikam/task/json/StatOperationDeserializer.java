package ru.aikam.task.json;

import com.google.gson.Gson;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.aikam.task.entity.input.StatOperationCriterion;

/**
 * Класс десериализующий строку формата json в класс StatOperation
 *
 * @author Daniil Makarov (Kami)
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StatOperationDeserializer {
    /**
     * @param json строка формата json
     * @return экземпляр класса StatOperation
     */
    public static StatOperationCriterion fromJson(String json) {
        return new Gson().fromJson(json, StatOperationCriterion.class);
    }
}
