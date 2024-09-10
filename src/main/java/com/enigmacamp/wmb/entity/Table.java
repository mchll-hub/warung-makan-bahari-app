package com.enigmacamp.wmb.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@jakarta.persistence.Table(name = "m_table")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Table {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "name", nullable = false, unique = true)
    private String name;
}
