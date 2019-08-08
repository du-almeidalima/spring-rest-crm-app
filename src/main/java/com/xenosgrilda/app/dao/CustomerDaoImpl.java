package com.xenosgrilda.app.dao;

import com.xenosgrilda.app.entities.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerDaoImpl implements CustomerDAO {

    // Injecting Hibernate session factory from AppConfig
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Customer> getCustomers() {
        Session session = this.sessionFactory.getCurrentSession();

        // Spring will look for this class based on "setPackagesToScan()" on AppConfig
        Query<Customer> query = session.createQuery("FROM Customer ORDER BY lastName", Customer.class);

        List<Customer> customers = query.getResultList();

        return customers;
    }

    @Override
    public Customer getCustomer(int id) {
        Session session = this.sessionFactory.getCurrentSession();

        return session.get(Customer.class, id);
    }

    @Override
    public void saveCustomer(Customer customer) {

        Session session = this.sessionFactory.getCurrentSession();

        session.saveOrUpdate(customer); // Basically ABAP "MODIFY"
    }

    @Override
    public void deleteCustomer(int id) {

        Session session = this.sessionFactory.getCurrentSession();

        Query query = session.createQuery("DELETE FROM Customer cu WHERE cu.id = :customerId")
                .setParameter("customerId", id);

        query.executeUpdate();
    }
}

/**
 * Session Factory : Note that this field name should be the same as the SessionFactory ID defined in the dispatcher-servlet.xml
 * @Repository :  This is also a spring-framework's annotation. When you annotate a class @Repository, spring container understands it's a DAO class and translates
 * all unchecked exceptions (thrown from DAO methods) into Spring DataAccessException. DAO class is the class where you write methods to perform operations over db.
 */
