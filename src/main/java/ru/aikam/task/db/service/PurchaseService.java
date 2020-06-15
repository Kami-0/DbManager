package ru.aikam.task.db.service;

import lombok.NoArgsConstructor;
import ru.aikam.task.db.dao.PurchaseDao;
import ru.aikam.task.db.model.Customer;
import ru.aikam.task.db.model.Product;
import ru.aikam.task.db.model.Purchase;

import java.sql.Date;
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
    private final PurchaseDao purchaseDao = new PurchaseDao();

    /**
     * Возращает из БД список всех покупателей купивших этот товар не менее, чем
     * указанное число раз
     *
     * @param product  продукт
     * @param minTimes минимальное число покупок
     * @return мапа покупателей
     */
    public Map<Customer, Long> findAllGoodCustomers(Product product, int minTimes) {
        List<Purchase> purchases = purchaseDao.findAllPurchaseByProductName(product);
        Map<Customer, Long> customerLongMap = purchases.stream()
                .collect(Collectors.groupingBy(Purchase::getCustomerId, Collectors.counting()));
        Map<Customer, Long> filteredCustomerLongMap = new LinkedHashMap<>();
        customerLongMap.forEach((customer, value) -> {
            if (value >= minTimes) {
                filteredCustomerLongMap.put(customer, value);
            }
        });
        return filteredCustomerLongMap;
    }

    /**
     * Метод возращает покупателей, у которых
     * общая стоимость всех покупок за всё время попадает в интервал
     *
     * @param minExpenses мин сумма покупки
     * @param maxExpenses максимальная сумма покупки
     * @return мапа покупаетелей
     */
    public Map<Customer, Long> findAllCustomersWithPurchaseFiltering(int minExpenses, int maxExpenses) {
        List<Purchase> purchases = purchaseDao.findAllPurchase();
        Map<Customer, Long> customerLongMap = purchases.stream()
                .collect(Collectors.groupingBy(Purchase::getCustomerId, Collectors.summingLong(purchase -> purchase.getProductId().getExpense())));
        Map<Customer, Long> filteredCustomerLongMap = new LinkedHashMap<>();
        customerLongMap.forEach((customer, value) -> {
            if (value >= minExpenses && value <= maxExpenses) {
                filteredCustomerLongMap.put(customer, value);
            }
        });
        return filteredCustomerLongMap;
    }

    /**
     * Возращает из БД мапу покупателей с их товарами
     *
     * @return мапу покупателей с их товарами в отсортированом по возрастанию виде
     */
    public LinkedHashMap<Customer, Long> findCustomersToNumberPurchases() {
        List<Purchase> purchases = purchaseDao.findAllPurchase();
        Map<Customer, Long> customerLongMap = groupingByCustomersToNumberPurchases(purchases);
        return sortMapAscending(customerLongMap);
    }

    /**
     * Метод группирует список покупок на покупателя и количество его покупок
     *
     * @param purchases список покупок
     * @return разгруппированный список покупок на покупателя и количество его покупок
     */
    private Map<Customer, Long> groupingByCustomersToNumberPurchases(List<Purchase> purchases) {
        return purchases.stream()
                .collect(Collectors.groupingBy(Purchase::getCustomerId, Collectors.counting()));
    }

    /**
     * Метод сортирует мапу по покупателю и количество его покупок по возрастанию
     *
     * @param customerLongMap мапа по покупателю и количество его покупок
     * @return мапу по покупателю и количество его покупок по возрастанию
     */
    private LinkedHashMap<Customer, Long> sortMapAscending(Map<Customer, Long> customerLongMap) {
        return customerLongMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey,
                        Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    /**
     * Метод ищет все покупки сделанные за определенный временой промежуток
     *
     * @param startDate начальная дата включительно
     * @param endDate   конечная дата включительно
     * @return все покупки сделанные за определенный временой промежуток
     */
    public List<Purchase> findAllInDateRange(Date startDate, Date endDate) {
        return purchaseDao.findAllInDateRange(startDate, endDate);
    }
}
