package com.enigmacamp.wmb.service.impl;

import com.enigmacamp.wmb.dto.request.OrderDetailRequest;
import com.enigmacamp.wmb.dto.request.OrderRequest;
import com.enigmacamp.wmb.dto.response.OrderDetailResponse;
import com.enigmacamp.wmb.dto.response.OrderResponse;
import com.enigmacamp.wmb.entity.*;
import com.enigmacamp.wmb.repository.OrderRepository;
import com.enigmacamp.wmb.service.CustomerService;
import com.enigmacamp.wmb.service.MenuService;
import com.enigmacamp.wmb.service.OrderDetailService;
import com.enigmacamp.wmb.service.TableService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderServiceImpl;

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderDetailService orderDetailService;
    @Mock
    private MenuService menuService;
    @Mock
    private CustomerService customerService;
    @Mock
    private TableService tableService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createNewTransaction() {
        OrderRequest request = new OrderRequest();
        request.setCustomerId("1");
        request.setTableName("T01");

        //set up mock customer
        Customer customer = Customer.builder()
                .id("1")
                .name("Michelle")
                .build();
        when(customerService.getById(customer.getId())).thenReturn(customer);

        //set up mock table
        Table table = Table.builder()
                .name("T01")
                .build();
        when(tableService.getByName(table.getName())).thenReturn(table);

        //set up mock for order
        Order order = Order.builder()
                .id("123")
                .customer(customer)
                .table(table)
                .transDate(LocalDateTime.now())
                .build();
        when(orderRepository.saveAndFlush(any(Order.class))).thenReturn(order);

        //set up order detail
        OrderDetailRequest orderDetailRequest = new OrderDetailRequest();
        orderDetailRequest.setMenuId("1");
        orderDetailRequest.setQty(2);
        request.setOrderDetails(Collections.singletonList(orderDetailRequest));

        Menu menu = Menu.builder()
                .id("1")
                .price(10000L)
                .build();
        when(menuService.getById(menu.getId())).thenReturn(menu);

        List<OrderDetail> orderDetails = new ArrayList<>();
        OrderDetail orderDetail = OrderDetail.builder()
                .id("456")
                .quantity(2)
                .price(10000L)
                .menu(menu)
                .build();
        orderDetails.add(orderDetail);
        when(orderDetailService.createBulk(anyList())).thenReturn(orderDetails);
        OrderResponse response = orderServiceImpl.createNewTransaction(request);

        assertNotNull(response);
    }

    @Test
    void getAllOrders_WhenOrderExist_ShouldReturnOrderResponse() {
        Customer customer = Customer.builder()
                .id("1")
                .name("Michelle")
                .build();
        when(customerService.getById(customer.getId())).thenReturn(customer);

        Table table = Table.builder()
                .name("T01")
                .build();
        when(tableService.getByName(table.getName())).thenReturn(table);

        Menu menu = Menu.builder()
                .id("1")
                .price(10000L)
                .build();
        when(menuService.getById(menu.getId())).thenReturn(menu);

        List<Order> orders = Collections.singletonList(Order.builder()
                .id("123")
                .customer(customer)
                .table(table)
                .transDate(LocalDateTime.now())
                .build());

        List<OrderDetail> orderDetails = Collections.singletonList(OrderDetail.builder()
                .id("123")
                .quantity(3)
                .order(orders.get(0))
                .price(10000L)
                .menu(menu)
                .build());

        when(orderRepository.findAll()).thenReturn(orders);

        List<OrderDetailResponse> orderDetailResponseList = Collections.singletonList(OrderDetailResponse.builder()
                        .orderDetailId(orderDetails.get(0).getId())
                        .orderId(orders.get(0).getId())
                        .menuId(orderDetails.get(0).getMenu().getId())
                        .price(orderDetails.get(0).getPrice())
                        .quantity(orderDetails.get(0).getQuantity())
                .build());

        List<OrderResponse> response = Collections.singletonList(OrderResponse.builder()
                        .orderId(orders.get(0).getId())
                        .customerId(customer.getId())
                        .tableName(table.getName())
                        .transDate(LocalDateTime.now())
                        .orderDetails(orderDetailResponseList)
                .build());

        assertNotNull(response);

    }

    @Test
    void getAll_WhenCustomerNotFound_ShouldThrowNotFoundException() {
        when(orderRepository.findAll()).thenReturn(Collections.emptyList());
        List<OrderResponse> list = orderServiceImpl.getAll();
        assertNotNull(list);
        assertTrue(list.isEmpty());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void getById_WhenOrderFound_ShouldReturnOrderResponse() {
        Customer customer = Customer.builder()
                .id("1")
                .name("Michelle")
                .build();
        when(customerService.getById(customer.getId())).thenReturn(customer);

        Table table = Table.builder()
                .name("T01")
                .build();
        when(tableService.getByName(table.getName())).thenReturn(table);

        Menu menu = Menu.builder()
                .id("1")
                .price(10000L)
                .build();
        when(menuService.getById(menu.getId())).thenReturn(menu);

        Order order = Order.builder()
                .id("1")
                .customer(customer)
                .table(table)
                .transDate(LocalDateTime.now())
                .build();

        List<OrderDetail> orderDetails = Collections.singletonList(OrderDetail.builder()
                        .id("123")
                        .quantity(3)
                        .order(order)
                        .price(10000L)
                        .menu(menu)
                .build());

        order.setOrderDetails(orderDetails);
        when(orderRepository.findById(customer.getId())).thenReturn(Optional.of(order));

        OrderResponse response = orderServiceImpl.getById(order.getId());

        assertNotNull(response);

    }

    @Test
    void getById_WhenCustomerNotFound_ShouldThrowNotFoundException() {
        String id = "123";

        when(orderRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> orderServiceImpl.getById(id));

        assertEquals("Order not found", exception.getMessage());
        verify(orderRepository).findById(id);
    }

}