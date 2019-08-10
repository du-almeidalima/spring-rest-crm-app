package com.xenosgrilda.app.services;

import com.xenosgrilda.app.entities.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> getCustomers();

    Customer getCustomer(int id);

    void addCustomer(Customer customer);

    void deleteCustomer(int id);
}
