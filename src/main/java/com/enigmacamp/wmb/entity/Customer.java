package com.enigmacamp.wmb.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name="m_customer")
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "phone_number", unique = true)
    private String phoneNumber;
    @Column(name = "is_member")
    private Boolean isMember = false;
    @OneToOne
    @JoinColumn(name = "m_user_credential_id", unique = true)
    private UserCredential userCredential;

}

