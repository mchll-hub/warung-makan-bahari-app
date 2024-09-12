package com.enigmacamp.wmb.service.impl;

import com.enigmacamp.wmb.dto.request.PagingCustomerRequest;
import com.enigmacamp.wmb.entity.Customer;
import com.enigmacamp.wmb.repository.CustomerRepository;
import com.enigmacamp.wmb.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    public Customer createNew(Customer customer){
        return customerRepository.save(customer);
    }

    public Customer getById(String id){
        return findByIdOrThrowNotFound(id);
    }

    public Page<Customer> getAll(PagingCustomerRequest request){
        Pageable pageable = PageRequest.of(request.getPage()-1, request.getSize());
        return customerRepository.findAll(pageable);

    }

    public Customer update(Customer customer){
        findByIdOrThrowNotFound(customer.getId());
        return customerRepository.save(customer);
    }

    public void deleteById(String id){
        Customer customer = findByIdOrThrowNotFound(id);
        customerRepository.delete(customer);
    }

    private Customer findByIdOrThrowNotFound(String id){
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.orElseThrow(() -> new RuntimeException ("Customer not found"));
    }

}
