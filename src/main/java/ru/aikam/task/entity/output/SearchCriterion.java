package ru.aikam.task.entity.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.aikam.task.entity.Criterion;

import java.util.LinkedList;
import java.util.List;

/**
 * Класс сущность ответа по критерию пользователя запроса типа search
 *
 * @author Daniil Makarov (Kami)
 */
@Setter
@Getter
@AllArgsConstructor
public class SearchCriterion {
    private Criterion criterion;
    private List<SearchResult> results = new LinkedList<>();

}
