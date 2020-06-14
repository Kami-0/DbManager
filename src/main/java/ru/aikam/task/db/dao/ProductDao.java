package ru.aikam.task.db.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import ru.aikam.task.db.model.Product;
import ru.aikam.task.db.util.HibernateSessionFactoryUtil;

public class ProductDao {
    public Product findByProductName(String productName) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Criteria customerCriteria = session.createCriteria(Product.class);
        customerCriteria.add(Restrictions.eq("productName", productName));
        return (Product) customerCriteria.uniqueResult();
    }
}
