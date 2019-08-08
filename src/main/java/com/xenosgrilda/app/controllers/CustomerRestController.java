package com.xenosgrilda.app.controllers;

import com.xenosgrilda.app.entities.Customer;
import com.xenosgrilda.app.exceptions.CustomerNotFoundException;
import com.xenosgrilda.app.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerRestController {

    @Autowired
    private CustomerService customerService;


    // GET
    @GetMapping("/customers")
    public List<Customer> getCustomers(){

        // Getting customers from the Service
        return this.customerService.getCustomers();
    }

    @GetMapping("/customers/{id}")
    public Customer getCustomer(@PathVariable int id){

        // This is kinda tricky, if the customer is not found, it returns null, and the Jackson will convert it to a
        // empty response body! not and Error! Also if id receives a string value it will also crash!

        Customer customer = this.customerService.getCustomer(id);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer ID not found");
        }else {
            return customer;
        }
    }

}

/**
 * Note that for "getCustomers()" we're basically returning a List<Customers>, but this is a HttpRequest, how is that
 * possible???
 * This is possible because of the interaction between Spring and Jackson's Project. Whenever Jackson's is on the
 * class path, installed via pom.xml, Spring automatically calls it when returning something from a @RestController
 */