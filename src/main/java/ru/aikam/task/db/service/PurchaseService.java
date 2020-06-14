package ru.aikam.task.db.service;

import lombok.NoArgsConstructor;
import ru.aikam.task.api.CustomerToSearchResultAdapter;
import ru.aikam.task.db.dao.PurchaseDao;
import ru.aikam.task.db.model.Customer;
import ru.aikam.task.db.model.Product;
import ru.aikam.task.db.model.Purchase;
import ru.aikam.task.entity.output.SearchResult;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Класс для работы с PurchaseDao
 *
 * @author Daniil Makarov (Kami)
 */
@NoArgsConstructor
public class PurchaseService {
    private PurchaseDao purchaseDao = new PurchaseDao();

    /**
     * Возращает из БД список всех покупателей купивших этот товар не менее, чем
     * указанное число раз
     *
     * @param product  продукт
     * @param minTimes минимальное число покупок
     * @return список всех покупателей
     */
    public List<SearchResult> findAllCustomersByProductNameAndMinTimes(Product product, int minTimes) {
        List<Purchase> purchases = purchaseDao.findAllPurchaseByProductName(product);
        Map<Customer, Long> customerLongMap = purchases.stream()
                .collect(Collectors.groupingBy(Purchase::getCustomerId, Collectors.counting()));
        List<SearchResult> searchResults = new ArrayList<>();
        customerLongMap.forEach((customer, value) -> {
            if (value >= minTimes) {
                searchResults.add(CustomerToSearchResultAdapter.valueOf(customer));
            }
        });
        return searchResults;
    }

    /**
     * Метод возращает покупателей, у которых
     * общая стоимость всех покупок за всё время попадает в интервал
     *
     * @param minExpenses мин сумма покупки
     * @param maxExpenses максимальная сумма покупки
     * @return список покупаетелей
     */
    public List<SearchResult> findAllCustomersWithPurchaseFiltering(int minExpenses, int maxExpenses) {
        List<Purchase> purchases = purchaseDao.findAllPurchase();
        Map<Customer, Long> customerLongMap = purchases.stream()
                .collect(Collectors.groupingBy(Purchase::getCustomerId, Collectors.summingLong(purchase -> purchase.getProductId().getExpense())));
        List<SearchResult> searchResults = new ArrayList<>();
        customerLongMap.forEach((customer, value) -> {
            if (value >= minExpenses && value <= maxExpenses) {
                searchResults.add(CustomerToSearchResultAdapter.valueOf(customer));
            }
        });
        return searchResults;
    }

    /**
     * Возращает из БД список неактивных покупателей
     *
     * @param badCustomersNumber количество неактивных покупателей
     * @return список покупаетелей
     */
    public List<SearchResult> findBadCustomers(int badCustomersNumber) {
        List<Purchase> purchases = purchaseDao.findAllPurchase();
        Map<Customer, Long> customerLongMap = purchases.stream()
                .collect(Collectors.groupingBy(Purchase::getCustomerId, Collectors.counting()));

        LinkedHashMap<Customer, Long> customerLongSortedMap = customerLongMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey,
                        Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        List<SearchResult> searchResults = new ArrayList<>();

        customerLongSortedMap.entrySet()
                .stream()
                .limit(badCustomersNumber)
                .forEach(x -> searchResults.add(CustomerToSearchResultAdapter.valueOf(x.getKey())));
        return searchResults;
    }
}
