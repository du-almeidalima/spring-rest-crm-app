package com.xenosgrilda.app.dao;


import com.xenosgrilda.app.entities.Customer;

import java.util.List;

public interface CustomerDAO {

    List<Customer> getCustomers();

    Customer getCustomer(int id);

    void saveCustomer(Customer customer);

    void deleteCustomer(int id);
}

/**
 * DAO (Data Access Object) is the layer that will interact with the Database, it should only have basic read/write
 * operations.
 *
 * It should only have methods for the pertaining Class, in this case, the Customer. The DAO should never have any business logic.
 * That is a responsibility of the Service layer.
 *
 * Read more about DAO: https://pt.stackoverflow.com/questions/113840/como-funciona-o-padr%C3%A3o-dao
**/