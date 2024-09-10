package com.enigmacamp.wmb.service;

import com.enigmacamp.wmb.dto.response.OrderDetailResponse;
import com.enigmacamp.wmb.dto.response.OrderResponse;
import com.enigmacamp.wmb.entity.OrderDetail;

import java.util.List;

public interface OrderDetailService {
    List<OrderDetail> createBulk(List<OrderDetail> orderDetails);
    OrderDetailResponse getById(String id);
}
