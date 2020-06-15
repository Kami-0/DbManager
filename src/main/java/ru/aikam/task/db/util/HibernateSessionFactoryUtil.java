package ru.aikam.task.db.util;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.spi.ServiceException;
import ru.aikam.task.db.model.Customer;
import ru.aikam.task.db.model.Product;
import ru.aikam.task.db.model.Purchase;

/**
 * Класс фабрика для созданий сессий с БД
 *
 * @author Daniil Makarov (Kami)
 */
@NoArgsConstructor
@Slf4j
public class HibernateSessionFactoryUtil {
    private static SessionFactory sessionFactory;

    /**
     * Создается сессию работы с БД и возращает её
     *
     * @return сессию работы с БД
     */
    public static SessionFactory getSessionFactory() throws ServiceException {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration().configure();
            configuration.addAnnotatedClass(Customer.class);
            configuration.addAnnotatedClass(Product.class);
            configuration.addAnnotatedClass(Purchase.class);
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
            sessionFactory = configuration.buildSessionFactory(builder.build());
        }
        return sessionFactory;
    }
}
