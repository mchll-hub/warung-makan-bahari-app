package com.enigmacamp.wmb.controller;

import com.enigmacamp.wmb.dto.request.NewCustomerRequest;
import com.enigmacamp.wmb.dto.request.PagingCustomerRequest;
import com.enigmacamp.wmb.dto.response.CustomerResponse;
import com.enigmacamp.wmb.entity.Customer;
import com.enigmacamp.wmb.service.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.postgresql.hostchooser.HostRequirement.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureWebMvc
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

//    @InjectMocks
//    CustomerController customerController;

    @MockBean
    private CustomerService customerService;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CustomerController customerController;

    @BeforeEach
    void setUp() {
//        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new CustomerController(customerService)).build();
        objectMapper = new ObjectMapper();
    }

    @WithMockUser
    @Test
    void createNewCustomer_ShouldReturnCreatedCustomer() throws Exception {
        //Arrange
        NewCustomerRequest request = new NewCustomerRequest();
        request.setName("Michelle");
        request.setPhoneNumber("089746532");

        CustomerResponse customerResponse = CustomerResponse.builder()
                .customerId("123")
                .name("Michelle")
                .phoneNumber("089746532")
                .build();

        when(customerService.createNew(any(NewCustomerRequest.class))).thenReturn(customerResponse);

        //Act & Assert
        mockMvc.perform(post("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.name").value("Michelle"))
                .andExpect(jsonPath("$.data.phoneNumber").value("089746532"));
        verify(customerService, times(1)).createNew(any(NewCustomerRequest.class));
    }

    @Test
    void getCustomerById() throws Exception {
        Customer customer = new Customer();
        customer.setId("123");
        customer.setName("Michelle");
        customer.setPhoneNumber("089746532");
        customer.setIsMember(true);

        CustomerResponse customerResponse = CustomerResponse.builder()
                .customerId("123")
                .name("Michelle")
                .phoneNumber("089746532")
                .isMember(true)
                .build();

        when(customerService.getById(any())).thenReturn(customer);

        mockMvc.perform(get("/api/v1/customers/{id}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value("123"))
                .andExpect(jsonPath("$.data.name").value("Michelle"))
                .andExpect(jsonPath("$.data.phoneNumber").value("089746532"));
        verify(customerService, times(1)).getById(any());
    }

    @Test
    void getAllCustomer() {
//        //arrange
//        int page = 1;
//        int size = 5;
//
//        //Dummy data untuk Page<CustomerResponse>
//        List<CustomerResponse> customerResponseList = Collections.singletonList(
//                new CustomerResponse("123", "Michelle","089746532", true));
//
//        Page<CustomerResponse> pageCustomerResponse = new PageImpl<>(customerResponseList, PageRequest.of(page,size), 2);
//
//        //ketika customerService.getAll dipanggil, kembalikan dummy pageCustomerResponse
//        when(customerService.getAll(any(PagingCustomerRequest.class))).thenReturn(pageCustomerResponse);
//
//        //act
//        ResponseEntity responseEntity = customerController.getAllCustomer(page, size);
//
//        //assert
//        assertNotNull(responseEntity);
//        assertEquals();

    }

    @Test
    void updateCustomer() {
    }

    @Test
    void deleteCustomerById() {
    }
}