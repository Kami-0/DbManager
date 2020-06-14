package ru.aikam.task.api;

import ru.aikam.task.db.model.Customer;
import ru.aikam.task.db.model.Product;
import ru.aikam.task.db.model.Purchase;
import ru.aikam.task.db.service.CustomerService;
import ru.aikam.task.db.service.ProductService;
import ru.aikam.task.db.service.PurchaseService;
import ru.aikam.task.entity.Criterion;
import ru.aikam.task.entity.input.*;
import ru.aikam.task.entity.output.*;
import ru.aikam.task.exception.InvalidDateRangeException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Класс для обработки операций пользователя
 *
 * @author Daniil Makarov (Kami)
 */
public class CriterionHandler {
    private static final long MILLISECONDS_IN_ONE_DAY = 86_400_000L;
    private static final long MIN_ALLOWABLE_DAYS = 1;
    private static final long INCLUSIVE_DAY = 1;

    /**
     * Метод обрабатывает критерии запроса search
     *
     * @param searchOperation операция типа search
     * @return ответ на запрос типа search
     */
    public SearchResponse handleSearchOperation(SearchOperation searchOperation) {
        SearchResponse outputRequest = new SearchResponse();
        for (Criterion criterion : searchOperation.getCriteria()) {
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

    /**
     * Метод обрабатывает критерий запроса stat
     *
     * @param criteria операция типа stat
     * @return ответ на запрос типа stat
     */
    public StatResponse handleStatOperation(StatOperationCriterion criteria) {
        StatResponse statResponse = new StatResponse();
        long millisecondsDifference = criteria.getEndDate().getTime() - criteria.getStartDate().getTime();
        int daysDifference = (int) (millisecondsDifference / MILLISECONDS_IN_ONE_DAY + INCLUSIVE_DAY);
        if (daysDifference < MIN_ALLOWABLE_DAYS) {
            throw new InvalidDateRangeException("Invalid date range " + criteria.getStartDate() + " - " + criteria.getEndDate());
        }
        statResponse.setTotalDays(daysDifference);

        List<StatCustomer> statCustomerList = handleBadStatCriterion(criteria);
        statResponse.setCustomers(statCustomerList);

        int totalExpenses = statCustomerList.stream().mapToInt(StatCustomer::getTotalExpenses).sum();
        statResponse.setTotalExpenses(totalExpenses);

        OptionalDouble avgExpensesOptional = statCustomerList.stream().mapToInt(StatCustomer::getTotalExpenses).average();
        if (avgExpensesOptional.isPresent()) {
            statResponse.setAvgExpenses(avgExpensesOptional.getAsDouble());
        } else {
            statResponse.setAvgExpenses(0);
        }
        return statResponse;
    }

    /**
     * Метод обрабатывает критерий типа lastName
     *
     * @param criterion обрабатываемый критерий
     * @return ответ запроса
     */
    private SearchCriterion handleLastNameCriterion(LastNameCriterion criterion) {
        CustomerService customerService = new CustomerService();
        List<SearchResult> searchResults = customerService.findAllByLastName(criterion.getLastName());
        return new SearchCriterion(criterion, searchResults);
    }

    /**
     * Метод обрабатывает критерий типа productName
     *
     * @param criterion обрабатываемый критерий
     * @return ответ запроса
     */
    private SearchCriterion handleProductNameCriterion(ProductNameCriterion criterion) {
        ProductService productService = new ProductService();
        Product product = productService.findByProductName(criterion.getProductName());
        PurchaseService purchaseService = new PurchaseService();
        List<SearchResult> searchResults = purchaseService.findAllCustomersByProductNameAndMinTimes(product, criterion.getMinTimes());
        return new SearchCriterion(criterion, searchResults);
    }

    /**
     * Метод обрабатывает критерий типа productExpenses
     *
     * @param criterion обрабатываемый критерий
     * @return ответ запроса
     */
    private SearchCriterion handleProductNameCriterion(ProductExpensesCriterion criterion) {
        PurchaseService purchaseService = new PurchaseService();
        List<SearchResult> searchResults = purchaseService.findAllCustomersWithPurchaseFiltering(criterion.getMinExpenses(), criterion.getMaxExpenses());
        return new SearchCriterion(criterion, searchResults);
    }

    /**
     * Метод обрабатывает критерий типа badCustomers
     *
     * @param criterion обрабатываемый критерий
     * @return ответ запроса
     */
    private SearchCriterion handleBadCustomersCriterion(BadCustomersCriterion criterion) {
        PurchaseService purchaseService = new PurchaseService();
        List<SearchResult> searchResults = purchaseService.findBadCustomers(criterion.getBadCustomers());
        return new SearchCriterion(criterion, searchResults);
    }

    /**
     * Метод обрабатывает критерий типа stat запроса stat
     *
     * @param criterion обрабатываемый критерий
     * @return ответ запроса
     */
    private List<StatCustomer> handleBadStatCriterion(StatOperationCriterion criterion) {
        PurchaseService purchaseService = new PurchaseService();
        List<Purchase> searchResults = purchaseService.findAllInDateRange(criterion.getStartDate(), criterion.getEndDate());
        Map<Customer, Map<String, Integer>> customerListMap = mapByCustomer(searchResults);
        List<StatCustomer> statCustomerList = parseToStatCustomer(customerListMap);
        return sortByTotalExpense(statCustomerList);
    }

    /**
     * Метод разбивает покупки на map по покупателю и его продуктам
     *
     * @param searchResults лист покупок
     * @return map по покупателю и его продуктам
     */
    private Map<Customer, Map<String, Integer>> mapByCustomer(List<Purchase> searchResults) {
        return searchResults.stream()
                .collect(Collectors.groupingBy(Purchase::getCustomerId,
                        Collectors.groupingBy(purchase -> purchase.getProductId().getProductName(),
                                Collectors.summingInt(purchase -> purchase.getProductId().getExpense()))));
    }

    /**
     * Метод парсить map по покупателю и его продуктам в список покупателей
     *
     * @param customerListMap map по покупателю и его продуктам
     * @return список покупателей
     */
    private List<StatCustomer> parseToStatCustomer(Map<Customer, Map<String, Integer>> customerListMap) {
        List<StatCustomer> statCustomerList = new LinkedList<>();
        customerListMap.forEach(((customer, stringIntegerMap) -> {
            String name = customer.getFullName();
            List<StatUniqueProduct> productList = StatUniqueProduct.valuesOf(stringIntegerMap);
            int totalExpenses = sumUpExpenses(productList);
            statCustomerList.add(new StatCustomer(name, productList, totalExpenses));
        }));
        return statCustomerList;
    }

    /**
     * Метод считает общую стоимость всего списка продуктов
     *
     * @param productList список продуктов
     * @return общая стоимость всех продуктов
     */
    private int sumUpExpenses(List<StatUniqueProduct> productList) {
        return productList.stream().mapToInt(StatUniqueProduct::getExpenses).sum();
    }

    /**
     * Метод сортирует по убыванию список покупателей
     *
     * @param statCustomerList список покупателей
     * @return отсортированный список покупателей
     */
    private List<StatCustomer> sortByTotalExpense(List<StatCustomer> statCustomerList) {
        return statCustomerList.stream()
                .sorted(Comparator.comparingInt(StatCustomer::getTotalExpenses))
                .collect(Collectors.toList());
    }
}
