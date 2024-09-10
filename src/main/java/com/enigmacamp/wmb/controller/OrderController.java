package com.enigmacamp.wmb.controller;

import com.enigmacamp.wmb.dto.request.OrderRequest;
import com.enigmacamp.wmb.dto.response.CommonResponse;
import com.enigmacamp.wmb.dto.response.OrderDetailResponse;
import com.enigmacamp.wmb.dto.response.OrderResponse;
import com.enigmacamp.wmb.repository.OrderDetailRepository;
import com.enigmacamp.wmb.service.OrderDetailService;
import com.enigmacamp.wmb.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transactions")
public class OrderController {

    private final OrderService orderService;
    private final OrderDetailService orderDetailService;

    @PostMapping()
    public ResponseEntity<CommonResponse<OrderResponse>> createNewTransaction(@RequestBody OrderRequest request){
        OrderResponse orderResponse = orderService.createNewTransaction(request);
        CommonResponse<OrderResponse> response = CommonResponse.<OrderResponse>builder()
                .message("Succesfully created new transaction")
                .statusCode(HttpStatus.CREATED.value())
                .data(orderResponse)
                .build();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping()
    public ResponseEntity<CommonResponse<List<OrderResponse>>> getAllTransaction(){
        List<OrderResponse> orderResponses = orderService.getAll();
        CommonResponse<List<OrderResponse>> response = CommonResponse. <List<OrderResponse>>builder()
                .message("Succesfully get all transactions")
                .statusCode(HttpStatus.OK.value())
                .data(orderResponses)
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id){
        OrderResponse orderResponse = orderService.getById(id);
        CommonResponse<OrderResponse> response = CommonResponse.<OrderResponse>builder()
                .message("Succesfully get transaction by id")
                .statusCode(HttpStatus.OK.value())
                .data(orderResponse)
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getDetailById(@PathVariable String id){
        OrderDetailResponse orderDetailResponse = orderDetailService.getById(id);
        CommonResponse<OrderDetailResponse> response = CommonResponse.<OrderDetailResponse>builder()
                .message("Succesfully get detail transaction by id")
                .statusCode(HttpStatus.OK.value())
                .data(orderDetailResponse)
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
