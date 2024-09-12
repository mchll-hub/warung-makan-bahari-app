package com.enigmacamp.wmb.controller;

import com.enigmacamp.wmb.dto.request.PagingCustomerRequest;
import com.enigmacamp.wmb.dto.response.CommonResponse;
import com.enigmacamp.wmb.dto.response.PagingResponse;
import com.enigmacamp.wmb.entity.Customer;
import com.enigmacamp.wmb.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

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
    public ResponseEntity<?> getAllCustomer(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "5") Integer size
    ){
        PagingCustomerRequest request = PagingCustomerRequest.builder()
                .page(page)
                .size(size)
                .build();
        Page<Customer> customers = customerService.getAll(request);

        PagingResponse pagingResponse = PagingResponse.builder()
                .page(page)
                .size(size)
                .count(customers.getTotalElements())
                .totalPages(customers.getTotalPages())
                .build();

        CommonResponse<List<Customer>> response = CommonResponse.<List<Customer>>builder()
                .message("Succesfully get all customer")
                .statusCode(HttpStatus.OK.value())
                .data(customers.getContent())
                .paging(pagingResponse)
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
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
