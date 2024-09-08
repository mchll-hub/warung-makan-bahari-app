package com.enigmacamp.wmb.repository;

import com.enigmacamp.wmb.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, String> {
    //query method -> membuat uery berdasarkan nama method
    //mencari semua data gunakan findAll
    //select * from m_menu where name = ?
    List<Menu> findAllByNameLikeIgnoreCaseOrPriceBetween(String name, Long minPrice, Long maxPrice);

}
