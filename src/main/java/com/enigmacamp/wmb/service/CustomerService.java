package com.enigmacamp.wmb.service;

import com.enigmacamp.wmb.entity.Customer;

import java.util.List;

public interface CustomerService {

    Customer createNew(Customer customer);

    Customer getById(String id);

    List<Customer> getAll();

    Customer update(Customer customer);

    void deleteById(String id);

}
