package com.enigmacamp.wmb.controller;

import com.enigmacamp.wmb.dto.request.NewCustomerRequest;
import com.enigmacamp.wmb.dto.request.PagingCustomerRequest;
import com.enigmacamp.wmb.dto.request.UpdateCustomerRequest;
import com.enigmacamp.wmb.dto.response.CommonResponse;
import com.enigmacamp.wmb.dto.response.CustomerResponse;
import com.enigmacamp.wmb.dto.response.PagingResponse;
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
    @PostMapping
    public ResponseEntity<?> createNewCustomer(@RequestBody NewCustomerRequest request) {
        CustomerResponse customerResponse = customerService.createNew(request);
        CommonResponse<CustomerResponse> response = CommonResponse.<CustomerResponse>builder()
                .message("successfully create new customer")
                .statusCode(HttpStatus.CREATED.value())
                .data(customerResponse)
                .build();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    //get by id
    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable String id) {
        CustomerResponse customerResponse = customerService.getOne(id);
        CommonResponse<CustomerResponse> response = CommonResponse.<CustomerResponse>builder()
                .message("successfully get customer by id")
                .statusCode(HttpStatus.OK.value())
                .data(customerResponse)
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
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
        Page<CustomerResponse> customers = customerService.getAll(request);

        PagingResponse pagingResponse = PagingResponse.builder()
                .page(page)
                .size(size)
                .count(customers.getTotalElements())
                .totalPages(customers.getTotalPages())
                .build();

        CommonResponse<List<CustomerResponse>> response = CommonResponse.<List<CustomerResponse>>builder()
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
    @PutMapping
    public ResponseEntity<?> updateCustomer(@RequestBody UpdateCustomerRequest request) {
        CustomerResponse customerResponse = customerService.update(request);
        CommonResponse<CustomerResponse> response = CommonResponse.<CustomerResponse>builder()
                .message("successfully update customer")
                .statusCode(HttpStatus.OK.value())
                .data(customerResponse)
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }


    //delete
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable String id) {
        customerService.deleteById(id);
        CommonResponse<?> response = CommonResponse.builder()
                .message("successfully update customer")
                .statusCode(HttpStatus.OK.value())
                .data("OK")
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }


}
