package com.enigmacamp.wmb.repository;

import com.enigmacamp.wmb.entity.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TableRepository extends JpaRepository<Table, String> {

    Optional<Table> findByName(String name);
}
