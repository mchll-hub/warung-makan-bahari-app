package com.enigmacamp.wmb.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Entity
@jakarta.persistence.Table(name = "t_order")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    public Customer customer;

    @ManyToOne
    @JoinColumn(name = "table_id")
    private Table table;

    @Column(name = "trans_date")
    private LocalDateTime transDate;

    @OneToMany(mappedBy = "order")
    @JsonManagedReference
    private List<OrderDetail> orderDetails;

}
