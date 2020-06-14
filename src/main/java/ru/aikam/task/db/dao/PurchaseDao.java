package ru.aikam.task.db.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import ru.aikam.task.db.model.Product;
import ru.aikam.task.db.model.Purchase;
import ru.aikam.task.db.util.HibernateSessionFactoryUtil;

import java.sql.Date;
import java.util.List;

public class PurchaseDao {
    public List<Purchase> findAllPurchaseByProductName(Product product) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Criteria purchaseCriteria = session.createCriteria(Purchase.class);
        purchaseCriteria.add(Restrictions.eq("productId", product));
        return (List<Purchase>) purchaseCriteria.list();
    }

    public List<Purchase> findAllPurchase() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Criteria purchaseCriteria = session.createCriteria(Purchase.class);
        return (List<Purchase>) purchaseCriteria.list();
    }

    public List<Purchase> findAllInDateRange(Date startDate, Date endDate) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Criteria purchaseCriteria = session.createCriteria(Purchase.class);
        purchaseCriteria.add(Restrictions.between("date", startDate, endDate));
        return (List<Purchase>) purchaseCriteria.list();
    }
}
