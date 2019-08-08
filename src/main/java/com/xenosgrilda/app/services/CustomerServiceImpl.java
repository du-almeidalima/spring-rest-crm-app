package com.xenosgrilda.app.services;

import com.xenosgrilda.app.dao.CustomerDAO;
import com.xenosgrilda.app.entities.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDAO customerDAO;

    @Override
    @Transactional
    public List<Customer> getCustomers() {

        return this.customerDAO.getCustomers();
    }

    @Override
    @Transactional
    public Customer getCustomer(int id) {
        return this.customerDAO.getCustomer(id);
    }

    @Override
    @Transactional
    public void saveCustomer(Customer customer) {

        this.customerDAO.saveCustomer(customer);
    }

    @Override
    @Transactional
    public void deleteCustomer(int id) {

        this.customerDAO.deleteCustomer(id);
    }
}

/**
 * @Service : annotation is also a specialization of the component annotation.
 * It doesn't currently provide any additional behavior over the @Component annotation, but it’s a good idea to use @Service over @Component in service-layer classes because it specifies intent better.
 * Additionally, tool support and additional behavior might rely on it in the future.
 * @Transactional : This annotation eliminates the need of starting a session "session.beginTransaction()" and the "session.getTransaction().commit()"
 */
