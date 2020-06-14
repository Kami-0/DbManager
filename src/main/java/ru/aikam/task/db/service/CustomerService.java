package ru.aikam.task.db.service;

import lombok.NoArgsConstructor;
import ru.aikam.task.api.CustomerToSearchResultAdapter;
import ru.aikam.task.db.dao.CustomerDao;
import ru.aikam.task.entity.output.SearchResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс для работы с CustomerDao
 *
 * @author Daniil Makarov (Kami)
 */
@NoArgsConstructor
public class CustomerService {
    private final CustomerDao customerDao = new CustomerDao();

    /**
     * Возращает из БД список всех покупателей с переданной в параметры фамилией
     *
     * @param lastName фамилия искомого покупателя
     * @return список всех покупателей с такой фамилией
     */
    public List<SearchResult> findAllByLastName(String lastName) {
        List<SearchResult> searchResults = new ArrayList<>();
        customerDao.findAllByLastName(lastName)
                .forEach(customer -> searchResults.add(CustomerToSearchResultAdapter.valueOf(customer)));
        return searchResults;
    }
}
