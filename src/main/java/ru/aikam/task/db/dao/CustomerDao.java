package ru.aikam.task.db.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import ru.aikam.task.db.model.Customer;
import ru.aikam.task.db.util.HibernateSessionFactoryUtil;

import java.util.List;

public class CustomerDao {
    public List<Customer> findAllByLastName(String lastName) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Criteria customerCriteria = session.createCriteria(Customer.class);
        customerCriteria.add(Restrictions.eq("lastName", lastName));
        return (List<Customer>) customerCriteria.list();
    }
}
