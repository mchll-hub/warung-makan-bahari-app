package com.enigmacamp.wmb.service.impl;

import com.enigmacamp.wmb.dto.request.NewCustomerRequest;
import com.enigmacamp.wmb.dto.request.PagingCustomerRequest;
import com.enigmacamp.wmb.dto.request.UpdateCustomerRequest;
import com.enigmacamp.wmb.dto.response.CustomerResponse;
import com.enigmacamp.wmb.entity.Customer;
import com.enigmacamp.wmb.repository.CustomerRepository;
import com.enigmacamp.wmb.util.ValidationUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CustomerServiceImplTest {

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ValidationUtil validationUtil;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createNew_WhenValidRequest_ShouldReturnCustomerResponse() {
        //A3(Arrange, Act, Assert

        //Arrange(persiapan)
        //preparation customer dto data
        NewCustomerRequest request = new NewCustomerRequest();
        request.setName("Michelle");
        request.setPhoneNumber("0890687465");

        //preparation customer entity data
        Customer customer = Customer.builder()
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .build();
        when(customerRepository.saveAndFlush(any(Customer.class))).thenReturn(customer);

        //Act(aksi/tindakan/perilaku)
        CustomerResponse response = customerService.createNew(request);

        //assert(evaluasi/pemeriksaan/pencocokan)
        assertNotNull(response);
        assertEquals(response.getName(), request.getName());
        assertEquals(response.getPhoneNumber(), request.getPhoneNumber());
    }

    @Test
    void createNew_WhenPhoneNumberAlreadyExists_ShouldThrowConflictException() {
        //arrange
        NewCustomerRequest request = new NewCustomerRequest();
        request.setName("Michelle");
        request.setPhoneNumber("0890687465");

        when(customerRepository.saveAndFlush(any(Customer.class))).thenThrow(new DataIntegrityViolationException("Phone number already exists"));
        //act & assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            customerService.createNew(request);
        });

        assertEquals("409 CONFLICT \"phone number already exist\"", exception.getMessage());
    }

    @Test
    void createNew_WhenValidRequest_ShouldReturnCustomerResponses() {
        Customer request = new Customer();
        request.setName("Michelle");
        request.setPhoneNumber("0890687465");
        request.setIsMember(true);

        Customer customer = Customer.builder()
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .isMember(request.getIsMember())
                .build();
        when(customerRepository.saveAndFlush(any(Customer.class))).thenReturn(customer);

    }

    @Test
    void getById() {
    }

    @Test
    void getOne_WhenCustomerExist_ShouldReturnCustomerResponse() {
        Customer customer = Customer.builder()
                .id("123")
                .name("Michelle")
                .phoneNumber("0890687465")
                .build();

        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));

        CustomerResponse response = customerService.getOne(customer.getId());

        assertNotNull(response);
        assertEquals(response.getName(), customer.getName());
        assertEquals(response.getPhoneNumber(), customer.getPhoneNumber());
        verify(customerRepository,times(1)).findById(customer.getId());
    }

    @Test
    void getOne_WhenCustomerNotFound_ShouldThrowNotFoundException() {

        when(customerRepository.findById("123")).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> customerService.getOne("123"));

        assertEquals("404 NOT_FOUND \"customer not found\"", exception.getMessage());
    }

    @Test
    void getAll_whenCustomerExists_ShouldReturnPageResponse() {
        Customer customer = Customer.builder()
            .id("123")
            .name("Michelle")
            .phoneNumber("0890687465")
            .build();

        PagingCustomerRequest pagingCustomerRequest = new PagingCustomerRequest();
        pagingCustomerRequest.setPage(1);
        pagingCustomerRequest.setSize(10);

        Pageable pageable = PageRequest.of(pagingCustomerRequest.getPage() -1, pagingCustomerRequest.getSize());

        Page<Customer> customerPage = new PageImpl<>(Collections.singletonList(customer),pageable,1);

        when(customerRepository.findAll(pageable)).thenReturn(customerPage);

        Page<CustomerResponse> resultt = customerService.getAll(pagingCustomerRequest);

        assertEquals(1, resultt.getTotalElements());
        verify(customerRepository,times(1)).findAll(pageable);
    }

    @Test
    void update() {

    }

    @Test
    void deleteById() {
    }
}