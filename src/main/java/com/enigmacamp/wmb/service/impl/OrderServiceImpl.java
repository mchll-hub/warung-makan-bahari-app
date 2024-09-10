package com.enigmacamp.wmb.service.impl;

import com.enigmacamp.wmb.dto.request.OrderDetailRequest;
import com.enigmacamp.wmb.dto.request.OrderRequest;
import com.enigmacamp.wmb.dto.response.OrderDetailResponse;
import com.enigmacamp.wmb.dto.response.OrderResponse;
import com.enigmacamp.wmb.entity.*;
import com.enigmacamp.wmb.repository.OrderRepository;
import com.enigmacamp.wmb.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailService orderDetailService;
    private final MenuService menuService;
    private final CustomerService customerService;
    private final TableService tableService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public OrderResponse createNewTransaction(OrderRequest request) {
        Customer customer = customerService.getById(request.getCustomerId());
        Table table = tableService.getByName(request.getTableName());

        //buat entity object
        Order order = Order.builder()
                .customer(customer)
                .table(table)
                .transDate(LocalDateTime.now())
                .build();

        orderRepository.saveAndFlush(order);

        List<OrderDetail> orderDetails = new ArrayList<>();

        for (OrderDetailRequest orderDetailRequest : request.getOrderDetails()) {
            Menu menu = menuService.getById(orderDetailRequest.getMenuId());
            //simpan order detail
            OrderDetail orderDetail = OrderDetail.builder()
                    .order(order)
                    .menu(menu)
                    .quantity(orderDetailRequest.getQty())
                    .price(menu.getPrice())
                    .build();
            orderDetails.add(orderDetail);
        }

        orderDetailService.createBulk(orderDetails);
        order.setOrderDetails(orderDetails);
        return mapToOrderResponse(order);
    }

    @Override
    public List<OrderResponse> getAll() {
        List<Order> orders = orderRepository.findAll();
        //cara pertama
        List<OrderResponse> orderResponses = new ArrayList<>();
        for (Order order : orders){
            OrderResponse orderResponse = mapToOrderResponse(order);
            orderResponses.add(orderResponse);
        }
        //cara kedua
//        List<OrderResponse> orderResponseList = orders.stream().map(order -> mapToOrderResponse(order)).collect(Collectors.toList());
//        List<OrderResponse> orderResponseList = orders.stream().map(this::mapToOrderResponse).collect(Collectors.toList());
        return orderResponses;
    }

    @Override
    public OrderResponse getById(String id) {
        Order order = (orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found")));
        return mapToOrderResponse(order);
    }

    private OrderResponse mapToOrderResponse(Order order){
        List<OrderDetailResponse> orderDetailResponses = order.getOrderDetails().stream().map(orderDetail -> {
            return OrderDetailResponse.builder()
                    .orderDetailId(orderDetail.getId())
                    .orderId(orderDetail.getOrder().getId())
                    .menuId(orderDetail.getMenu().getId())
                    .price(orderDetail.getPrice())
                    .quantity(orderDetail.getQuantity())
                    .build();
        }).toList();

        return OrderResponse.builder()
                .orderId(order.getId())
                .customerId(order.getCustomer().getId())
                .tableName(order.getTable().getName())
                .orderDetails(orderDetailResponses)
                .transDate(order.getTransDate())
                .build();
    }
//        Order order = new Order();
//
//        Customer customer = new Customer();
//        customer.setId(request.getCustomerId());
//        order.setCustomer(customer);
//
//        Table table = new Table();
//        table.setId(request.getTableId());
//        order.setTable(table);
//
//        order.setTransDate(LocalDateTime.now());
//
//        List<OrderDetail> orderDetails = new ArrayList<>();
//        for (OrderDetailRequest orderDetailRequest : request.getOrderDetails()){
//            OrderDetail orderDetail = new OrderDetail();
//
//            Menu menu = menuService.getById(orderDetailRequest.getMenuId());
//            menu.setId(orderDetailRequest.getMenuId());
//
//            orderDetail.setMenu(menu);
//            orderDetail.setPrice(menu.getPrice());
//            orderDetail.setQuantity(orderDetailRequest.getQty());
//
//            orderDetails.add(orderDetail);
//
//        }
//
//        order.setOrderDetails(orderDetails);
//
//        orderRepository.saveAndFlush(order);
//        orderDetailService.createBulk(order.getOrderDetails());
////        order.setTransDate(LocalDateTime.now());
//
//        for (OrderDetail orderDetail : order.getOrderDetails()){
////            Menu menu = menuService.getById(orderDetail.getMenu().getId());
//            orderDetail.setOrder(order);
////            orderDetail.setPrice(menu.getPrice());
//        }
//    return order;
//}



}
