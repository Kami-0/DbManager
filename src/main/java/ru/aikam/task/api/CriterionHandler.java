package ru.aikam.task.api;

import ru.aikam.task.db.service.CustomerService;
import ru.aikam.task.entity.Criterion;
import ru.aikam.task.entity.input.LastNameCriterion;
import ru.aikam.task.entity.input.SearchOperation;
import ru.aikam.task.entity.input.StatOperation;
import ru.aikam.task.entity.output.SearchCriterionRequest;
import ru.aikam.task.entity.output.SearchOutputRequest;
import ru.aikam.task.entity.output.SearchResult;

import java.util.List;

/**
 * Класс для обработки операций пользователя
 *
 * @author Daniil Makarov (Kami)
 */
public class CriterionHandler {
    /**
     * Метод обрабатывает критерии запроса search
     *
     * @param searchOperation операция типа search
     * @return ответ на запрос типа search
     */
    public SearchOutputRequest handleSearchOperation(SearchOperation searchOperation) {
        SearchOutputRequest outputRequest = new SearchOutputRequest();
        for (Criterion criterion : searchOperation.getCriteria()) {
            //todo после реализации основного функционала заменить свитч
            switch (criterion.type) {
                case "lastName":
                    outputRequest.addRequest(handleLastNameCriterion((LastNameCriterion) criterion));
                    break;
            }
        }
        return outputRequest;
    }

    public void handleStatOperation(StatOperation criteria) {

    }

    /**
     * Метод обрабатывает критерий типа lastName
     *
     * @param criterion обрабатываемый критерий
     * @return ответ запроса
     */
    public SearchCriterionRequest handleLastNameCriterion(LastNameCriterion criterion) {
        CustomerService customerService = new CustomerService();
        List<SearchResult> searchResults = customerService.findAllByLastName(criterion.getLastName());
        return new SearchCriterionRequest(criterion, searchResults);
    }
}
