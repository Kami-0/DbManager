package ru.aikam.task.db.service;

import lombok.NoArgsConstructor;
import ru.aikam.task.db.dao.ProductDao;
import ru.aikam.task.db.model.Product;

/**
 * Класс для работы с ProductDao
 *
 * @author Daniil Makarov (Kami)
 */
@NoArgsConstructor
public class ProductService {
    private ProductDao productDao = new ProductDao();

    /**
     * Ищет продукт по его названию в бд
     *
     * @param productName имя продукта
     * @return продукт
     */
    public Product findByProductName(String productName) {
        return productDao.findByProductName(productName);
    }
}
