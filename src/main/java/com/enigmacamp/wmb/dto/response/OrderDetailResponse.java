package com.enigmacamp.wmb.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailResponse {
    private String orderDetailId;
    private String orderId;
    private String menuId;
    private Long price;
    private Integer quantity;

}
