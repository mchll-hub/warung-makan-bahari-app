package com.enigmacamp.wmb.controller;

import com.enigmacamp.wmb.entity.Customer;
import com.enigmacamp.wmb.repository.CustomerRepository;
import com.enigmacamp.wmb.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    //create new customer
    @PostMapping()
    public Customer createNewCustomer(@RequestBody Customer customer){
        return customerService.createNew(customer);
    }

    //get by id
    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable String id){
        return customerService.getById(id);
    }

    //get all
    @GetMapping()
    public List<Customer> getAllCustomer(){
        return customerService.getAll();
    }

    //update
    @PutMapping()
    public Customer updateCustomer(@RequestBody Customer customer){
        return customerService.update(customer);
    }

    //delete
    @DeleteMapping("/{id}")
    public void deleteCustomerById(@PathVariable String id){
        customerService.deleteById(id);
    }

}
