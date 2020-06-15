package ru.aikam.task.db.service;

import lombok.NoArgsConstructor;
import ru.aikam.task.db.dao.CustomerDao;
import ru.aikam.task.db.model.Customer;

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
    public List<Customer> findAllByLastName(String lastName) {
        return customerDao.findAllByLastName(lastName);
    }
}
