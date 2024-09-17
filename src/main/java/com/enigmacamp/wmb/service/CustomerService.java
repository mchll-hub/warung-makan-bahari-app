package com.enigmacamp.wmb.service;

import com.enigmacamp.wmb.dto.request.NewCustomerRequest;
import com.enigmacamp.wmb.dto.request.PagingCustomerRequest;
import com.enigmacamp.wmb.dto.request.UpdateCustomerRequest;
import com.enigmacamp.wmb.dto.response.CustomerResponse;
import com.enigmacamp.wmb.entity.Customer;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomerService {

    CustomerResponse createNew (NewCustomerRequest request);
    CustomerResponse createNew(Customer customer);
    Customer getById(String id);
    CustomerResponse getOne (String id);
    Page<CustomerResponse> getAll(PagingCustomerRequest request);
    CustomerResponse update(UpdateCustomerRequest request);
    void deleteById(String id);

}
