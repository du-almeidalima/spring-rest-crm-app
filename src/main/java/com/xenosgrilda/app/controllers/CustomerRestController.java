package com.xenosgrilda.app.controllers;

import com.sun.istack.NotNull;
import com.xenosgrilda.app.entities.Customer;
import com.xenosgrilda.app.exceptions.CustomerEmptyBodyException;
import com.xenosgrilda.app.exceptions.CustomerNotFoundException;
import com.xenosgrilda.app.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // GET SINGLE
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

    // POST
    @PostMapping("/customers")
    public Customer addCustomer(@RequestBody @NotNull Customer newCustomer) {

        // We need to set id to 0 because in CustomerDAO we're using "session.saveOrUpdate", which basically is
        // If it don't find any customer with a given id, then it creates, otherwise it updates. By setting the id to
        // 0 we prevent a Customer from being updated by using this method, so Hibernate will always perform an INSERT
        newCustomer.setId(0);

        if (this.validateCustomer(newCustomer)) {

            throw new CustomerEmptyBodyException("Customer must have a First Name and a Last Name");

        } else {

            this.customerService.addCustomer(newCustomer);
            return newCustomer; // This will return the customer added in the db, with the ID set

        }
    }

    // PUT
    @PutMapping("/customers")
    public Customer updateCustomer(@RequestBody @NotNull Customer updatedCustomer) {

        // Remember in the "addCustomer", we needed to assign an "fake" id of 0 to prevent the Customer from being
        // updated, here we're doing the opposite, we're taking the new Customer data with @RequestBody by the help
        // of Jackson, and updating it based on the "id" that is coming from the request body
        if (this.validateCustomer(updatedCustomer)) {
            throw new CustomerEmptyBodyException("Customer must have a First Name and a Last Name");
        } else {
            // the "addCustomer" make use of saveOrUpdate method
            this.customerService.addCustomer(updatedCustomer);
        }

        return updatedCustomer;
    }

    // DELETE
    @DeleteMapping("/customers/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable int id) {

        // Getting the Customer and checking if it exists
        Customer customer = this.customerService.getCustomer(id);
        if (customer == null) {

            throw new CustomerNotFoundException("Cannot delete Customer ID: " + id + ": NOT FOUND");

        }else {

            this.customerService.deleteCustomer(id);
            // We can use ResponseEntity to customize our HTTP response
            return new ResponseEntity<>("Customer " + id + " was deleted.",HttpStatus.OK);

        }
    }



    // Helper method
    private boolean validateCustomer(Customer customer) {

        return customer.getFirstName() == null || customer.getLastName() == null;
    }
}





/**
 * Note that for "getCustomers()" we're basically returning a List<Customers>, but this is a HttpRequest, how is that
 * possible???
 * This is possible because of the interaction between Spring and Jackson's Project. Whenever Jackson's is on the
 * class path, installed via pom.xml, Spring automatically calls it when returning something from a @RestController
 */

/**
 * @RequestBody : Simply put, the @RequestBody annotation maps the HttpRequest body to a transfer or domain object,
 * enabling automatic deserialization of the inbound HttpRequest body onto a Java object.
 * In method "addCustomer" we're actually sending a JSON from the client, so in order for us to MAP that JSON incoming
 * RequestBody into a POJO we need to annotate it with @RequestBody so Jackson can convert it to us!
 */