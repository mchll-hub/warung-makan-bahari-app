package com.enigmacamp.wmb.service;

import com.enigmacamp.wmb.entity.Menu;
import org.springframework.stereotype.Service;

import java.util.List;

public interface MenuService {
    Menu createNew(Menu menu);

    Menu getById (String id);

    List<Menu> getAll(String name, Long minPrice, Long maxPrice);

    Menu update (Menu menu);

    void deleteById(String id);
}
