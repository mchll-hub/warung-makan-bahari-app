package com.enigmacamp.wmb.service;

import com.enigmacamp.wmb.dto.request.NewMenuRequest;
import com.enigmacamp.wmb.dto.response.MenuResponse;
import com.enigmacamp.wmb.entity.Menu;

import java.util.List;

public interface MenuService {
    MenuResponse createNewMenu(NewMenuRequest newMenuRequest);

    Menu getById (String id);

    List<Menu> getAllFilter(String name, Long minPrice, Long maxPrice);
    List<Menu> getAll();

    Menu update (Menu menu);

    void deleteById(String id);

}
