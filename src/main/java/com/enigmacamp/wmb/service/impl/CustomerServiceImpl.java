package com.enigmacamp.wmb.service.impl;

import com.enigmacamp.wmb.dto.request.NewCustomerRequest;
import com.enigmacamp.wmb.dto.request.PagingCustomerRequest;
import com.enigmacamp.wmb.dto.request.UpdateCustomerRequest;
import com.enigmacamp.wmb.dto.response.CustomerResponse;
import com.enigmacamp.wmb.entity.Customer;
import com.enigmacamp.wmb.repository.CustomerRepository;
import com.enigmacamp.wmb.service.CustomerService;
import com.enigmacamp.wmb.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ValidationUtil validationUtil;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CustomerResponse createNew(NewCustomerRequest request) {
        try {
            validationUtil.validate(request);
            Customer customer = Customer.builder()
                    .name(request.getName())
                    .phoneNumber(request.getPhoneNumber())
                    .build();
            customerRepository.saveAndFlush(customer);
            return mapToResponse(customer);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "phone number already exist");
        }
    }

    @Override
    public CustomerResponse createNew(Customer request) {
        Customer customer = customerRepository.saveAndFlush(request);
        return mapToResponse(customer);
    }

    @Transactional(readOnly = true)
    @Override
    public Customer getById(String id) {
        return findByIdOrThrowNotFound(id);
    }

    @Override
    public CustomerResponse getOne(String id) {
        Customer customer = findByIdOrThrowNotFound(id);
        return mapToResponse(customer);
    }


    @Transactional(readOnly = true)
    @Override
    public Page<CustomerResponse> getAll(PagingCustomerRequest request) {
        Pageable pageable = PageRequest.of(request.getPage() -1, request.getSize());
        Page<Customer> customers = customerRepository.findAll(pageable);
        return customers.map(customer -> mapToResponse(customer));
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public CustomerResponse update(UpdateCustomerRequest request) {
        try {
            Customer currentCustomer = findByIdOrThrowNotFound(request.getId());
            currentCustomer.setName(request.getName());
            currentCustomer.setPhoneNumber(request.getPhoneNumber());
            customerRepository.saveAndFlush(currentCustomer);
            return mapToResponse(currentCustomer);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "phone number already exist");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(String id) {
        Customer customer = findByIdOrThrowNotFound(id);
        customerRepository.delete(customer);
    }

    private Customer findByIdOrThrowNotFound(String id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "customer not found"));
    }

    private CustomerResponse mapToResponse(Customer customer) {
        return CustomerResponse.builder()
                .customerId(customer.getId())
                .name(customer.getName())
                .phoneNumber(customer.getPhoneNumber())
                .isMember(customer.getIsMember())
                .build();
    }
}

