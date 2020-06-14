package ru.aikam.task.json;

import com.google.gson.GsonBuilder;
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
        return new GsonBuilder().setDateFormat("yyyy-MM-dd").create().fromJson(json, StatOperationCriterion.class);
    }
}
