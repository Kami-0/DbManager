package ru.aikam.task.entity.input;

import lombok.Getter;
import lombok.Setter;
import ru.aikam.task.entity.Criterion;
import ru.aikam.task.json.StatOperationDeserializer;

/**
 * Класс-сущность запроса от пользователя типа stat
 *
 * @author Daniil Makarov (Kami)
 */
@Getter
@Setter
public final class StatOperationCriterion extends Criterion {
    private String startDate;
    private String endDate;

    public StatOperationCriterion(String startDate, String endDate) {
        super.type = "stat";
        this.startDate = startDate;
        this.endDate = endDate;
    }

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
        return false;
    }
}
