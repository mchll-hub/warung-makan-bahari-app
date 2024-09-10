package com.enigmacamp.wmb.dto.request;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private String customerId;
    private String tableName;
    private List<OrderDetailRequest> orderDetails;

}
