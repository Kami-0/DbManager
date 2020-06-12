package ru.aikam.task.api;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.aikam.task.db.model.Customer;
import ru.aikam.task.entity.output.SearchResult;

/**
 * Класс адаптер для классов SearchResult и Customer
 *
 * @author Daniil Makarov (Kami)
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CustomerToSearchResultAdapter {
    /**
     * Метод преобразает экземпляр типа Customer в экземпляр типа SearchResult
     *
     * @param customer экземпляр типа Customer
     * @return экземпляр типа SearchResult
     */
    public static SearchResult valueOf(Customer customer) {
        return new SearchResult(customer.getLastName(), customer.getFirstName());
    }
}
