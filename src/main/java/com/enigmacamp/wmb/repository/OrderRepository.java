package com.enigmacamp.wmb.repository;

import com.enigmacamp.wmb.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    //entiry order merupakan tabel transaksi sehingga tidak ada crud untuk delete dan update
    //hanya ada getById dan getALL
}
