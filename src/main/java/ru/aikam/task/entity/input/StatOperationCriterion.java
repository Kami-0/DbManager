package ru.aikam.task.entity.input;

import lombok.Getter;
import lombok.Setter;
import ru.aikam.task.entity.Criterion;
import ru.aikam.task.entity.DeserializableInJson;
import ru.aikam.task.json.StatOperationDeserializer;

import java.sql.Date;

/**
 * Класс-сущность запроса от пользователя типа stat
 *
 * @author Daniil Makarov (Kami)
 */
@Getter
@Setter
public final class StatOperationCriterion extends Criterion implements DeserializableInJson {
    private Date startDate;
    private Date endDate;

    /**
     * Метод десериализующий строку формата json в класс StatOperation
     *
     * @param json строка json
     * @return экземпляр класса на основе json
     */
    public static StatOperationCriterion fromJson(String json) {
        return StatOperationDeserializer.fromJson(json);
    }

    @Override
    public boolean isIncomplete() {
        return startDate == null || endDate == null;
    }
}
