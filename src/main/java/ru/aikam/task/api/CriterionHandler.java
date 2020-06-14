package ru.aikam.task.api;

import ru.aikam.task.db.model.Product;
import ru.aikam.task.db.service.CustomerService;
import ru.aikam.task.db.service.ProductService;
import ru.aikam.task.db.service.PurchaseService;
import ru.aikam.task.entity.Criterion;
import ru.aikam.task.entity.input.*;
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
                case "productName":
                    outputRequest.addRequest(handleProductNameCriterion((ProductNameCriterion) criterion));
                    break;
                case "productExpenses":
                    outputRequest.addRequest(handleProductNameCriterion((ProductExpensesCriterion) criterion));
                    break;
                case "badCustomers":
                    outputRequest.addRequest(handleBadCustomersCriterion((BadCustomersCriterion) criterion));
                    break;
            }
        }
        return outputRequest;
    }

    public void handleStatOperation(StatOperationCriterion criteria) {

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

    /**
     * Метод обрабатывает критерий типа productName
     *
     * @param criterion обрабатываемый критерий
     * @return ответ запроса
     */
    public SearchCriterionRequest handleProductNameCriterion(ProductNameCriterion criterion) {
        ProductService productService = new ProductService();
        Product product = productService.findByProductName(criterion.getProductName());
        PurchaseService purchaseService = new PurchaseService();
        List<SearchResult> searchResults = purchaseService.findAllCustomersByProductNameAndMinTimes(product, criterion.getMinTimes());
        return new SearchCriterionRequest(criterion, searchResults);
    }

    /**
     * Метод обрабатывает критерий типа productExpenses
     *
     * @param criterion обрабатываемый критерий
     * @return ответ запроса
     */
    public SearchCriterionRequest handleProductNameCriterion(ProductExpensesCriterion criterion) {
        PurchaseService purchaseService = new PurchaseService();
        List<SearchResult> searchResults = purchaseService.findAllCustomersWithPurchaseFiltering(criterion.getMinExpenses(), criterion.getMaxExpenses());
        return new SearchCriterionRequest(criterion, searchResults);
    }

    /**
     * Метод обрабатывает критерий типа badCustomers
     *
     * @param criterion обрабатываемый критерий
     * @return ответ запроса
     */
    public SearchCriterionRequest handleBadCustomersCriterion(BadCustomersCriterion criterion) {
        PurchaseService purchaseService = new PurchaseService();
        List<SearchResult> searchResults = purchaseService.findBadCustomers(criterion.getBadCustomers());
        return new SearchCriterionRequest(criterion, searchResults);
    }
}
