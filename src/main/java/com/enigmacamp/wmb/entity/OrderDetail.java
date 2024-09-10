package com.enigmacamp.wmb.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@jakarta.persistence.Table(name = "t_order_detail")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
//    @JsonIgnore
    @JsonBackReference
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @Column(columnDefinition = "BIGINT CHECK (price > 0)")
    private Long price;

    @Column(columnDefinition = "INT CHECK (quantity > 0)")
    private Integer quantity;
}