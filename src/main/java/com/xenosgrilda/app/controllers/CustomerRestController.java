package com.xenosgrilda.app.controllers;

import com.xenosgrilda.app.entities.Customer;
import com.xenosgrilda.app.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
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

    @GetMapping("/customers/{id}")
    public Customer getCustomer(@PathVariable int id){

        return this.customerService.getCustomer(id);
    }

    @PostMapping("add-customer")
    public String addCustomer(@ModelAttribute("customer") Customer customer){

        if (customer != null){
            this.customerService.saveCustomer(customer);
        }

        return "redirect:/customer/list";
    }

    @GetMapping("edit-customer-form")
    public String editCustomerForm(@RequestParam(value = "customerId", required = true) int id, Model model){
        // Getting customer from database and assigning it to model attribute
        Customer customer = this.customerService.getCustomer(id);
        model.addAttribute("editCustomer", customer);

        return "customers/edit-customer-form";
    }

    @GetMapping("/delete")
    public String deleteCustomerById(@RequestParam(value = "customerId", required = true) int id){
        // Deleting customer from database
        this.customerService.deleteCustomer(id);

        return "redirect:/customer/list";
    }
}

/**
 * Note that for "getCustomers()" we're basically returning a List<Customers>, but this is a HttpRequest, how is that
 * possible???
 * This is possible because of the interaction between Spring and Jackson's Project. Whenever Jackson's is on the
 * class path, installed via pom.xml, Spring automatically calls it when returning something from a @RestController
 */