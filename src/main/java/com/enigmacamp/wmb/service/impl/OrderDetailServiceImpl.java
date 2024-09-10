package com.enigmacamp.wmb.service.impl;

import com.enigmacamp.wmb.dto.response.OrderDetailResponse;
import com.enigmacamp.wmb.dto.response.OrderResponse;
import com.enigmacamp.wmb.entity.Order;
import com.enigmacamp.wmb.entity.OrderDetail;
import com.enigmacamp.wmb.repository.OrderDetailRepository;
import com.enigmacamp.wmb.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;

    @Override
    public List<OrderDetail> createBulk(List<OrderDetail> orderDetails) {
        return orderDetailRepository.saveAllAndFlush(orderDetails);
    }

    @Override
    public OrderDetailResponse getById(String id) {
        OrderDetail orderDetail = orderDetailRepository.findById(id).orElseThrow(() -> new RuntimeException("Order detail not found"));
        return OrderDetailResponse.builder()
                .orderDetailId(orderDetail.getId())
                .orderId(orderDetail.getOrder().getId())
                .menuId(orderDetail.getMenu().getId())
                .price(orderDetail.getMenu().getPrice())
                .quantity(orderDetail.getQuantity())
                .build();
    }
}
