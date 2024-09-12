package com.enigmacamp.wmb.service;

import com.enigmacamp.wmb.dto.request.PagingCustomerRequest;
import com.enigmacamp.wmb.entity.Customer;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomerService {

    Customer createNew(Customer customer);

    Customer getById(String id);

    Page<Customer> getAll(PagingCustomerRequest request);

    Customer update(Customer customer);

    void deleteById(String id);

}
