package ru.aikam.task.entity.input;

import lombok.Getter;
import lombok.Setter;
import ru.aikam.task.entity.Criterion;
import ru.aikam.task.entity.DeserializableInJson;
import ru.aikam.task.json.SearchOperationDeserializer;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс-сущность запроса от пользователя типа search
 *
 * @author Daniil Makarov (Kami)
 */
@Getter
@Setter
public final class SearchOperation implements DeserializableInJson {
    private List<Criterion> criteria = new ArrayList<>();

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

    /**
     * Метод проверяющий на пустоту список критериев
     *
     * @return true если список скритериев пустой, false если не пустой
     */
    public boolean isEmptyCriteriaList() {
        return criteria.isEmpty();
    }

    /**
     * Метод проверяющий на повреждения все критерии во время десериализации
     *
     * @return true если хоть один критерий поврежден, false если все целы
     */
    public boolean isIncompleteCriteriaList() {
        return criteria.stream().anyMatch(criterion -> {
            if (criterion == null) {
                return true;
            }
            return criterion.isIncomplete();
        });
    }
}
