package com.enigmacamp.wmb.service;

import com.enigmacamp.wmb.dto.request.OrderRequest;
import com.enigmacamp.wmb.dto.response.OrderResponse;
import com.enigmacamp.wmb.entity.Order;

import java.util.List;

public interface OrderService {
    OrderResponse createNewTransaction(OrderRequest orderRequest);
    List<OrderResponse> getAll();
    OrderResponse getById(String id);
}
