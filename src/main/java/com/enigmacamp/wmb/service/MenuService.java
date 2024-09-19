package com.enigmacamp.wmb.service;

import com.enigmacamp.wmb.dto.request.NewMenuRequest;
import com.enigmacamp.wmb.dto.request.UpdateMenuRequest;
import com.enigmacamp.wmb.dto.response.MenuResponse;
import com.enigmacamp.wmb.entity.Menu;
import org.springframework.core.io.Resource;

import java.util.List;

public interface MenuService {
    MenuResponse createNewMenu(NewMenuRequest newMenuRequest);
    List<MenuResponse> createBulk(List<NewMenuRequest> menus);
    MenuResponse getOne(String id);
    Resource getMenuImageById(String id);
    Menu getById (String id);
    List<Menu> getAllFilter(String name, Long minPrice, Long maxPrice);
    List<Menu> getAll();
    MenuResponse update (UpdateMenuRequest request);
    void deleteById(String id);

}
