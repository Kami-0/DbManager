package ru.aikam.task.entity.input;

import lombok.Getter;
import lombok.Setter;
import ru.aikam.task.entity.Criterion;
import ru.aikam.task.json.SearchOperationDeserializer;

import java.util.LinkedList;
import java.util.List;

/**
 * Класс-сущность запроса от пользователя типа search
 *
 * @author Daniil Makarov (Kami)
 */
@Getter
@Setter
public final class SearchOperation {
    private List<Criterion> criteria = new LinkedList<>();

    /**
     * Добавляет критерий в список критериев запроса search
     *
     * @param criterion критерий операции search
     */
    public void addCriterion(Criterion criterion) {
        criteria.add(criterion);
    }

    /**
     * Метод десериализующий строку формата json в класс SearchOperation
     *
     * @param json строка json
     * @return экземпляр класса на основе json
     */
    public static SearchOperation fromJson(String json) {
        return SearchOperationDeserializer.fromJson(json);
    }
}
