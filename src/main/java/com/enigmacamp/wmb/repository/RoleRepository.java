package com.enigmacamp.wmb.repository;

import com.enigmacamp.wmb.constant.ERole;
import com.enigmacamp.wmb.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByName(ERole name);
}
