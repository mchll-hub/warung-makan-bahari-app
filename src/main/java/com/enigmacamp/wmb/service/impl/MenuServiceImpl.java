package com.enigmacamp.wmb.service.impl;

import com.enigmacamp.wmb.dto.request.NewMenuRequest;
import com.enigmacamp.wmb.dto.response.MenuResponse;
import com.enigmacamp.wmb.entity.Menu;
import com.enigmacamp.wmb.repository.MenuRepository;
import com.enigmacamp.wmb.service.MenuService;
import com.enigmacamp.wmb.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final ValidationUtil validationUtil;

    @Override
    public MenuResponse createNewMenu(NewMenuRequest request) {
        validationUtil.validate(request);
        Menu menu = Menu.builder()
                .name(request.getName())
                .price(request.getPrice())
                .build();
        menuRepository.saveAndFlush(menu);
        return mapToResponse(menu);
    }

    @Override
    public Menu getById(String id) {
        return findByIdOrThrowNotFound(id);
    }

    @Override
    public List<Menu> getAllFilter(String name, Long minPrice, Long maxPrice) {
//        if (name == null){
//            return menuRepository.findAll();
//        }
        return menuRepository.findAllByNameLikeIgnoreCaseOrPriceBetween("%" +name+ "%", minPrice, maxPrice);
    }

    @Override
    public List<Menu> getAll() {
        return menuRepository.findAll();
    }

    @Override
    public Menu update(Menu menu) {
        findByIdOrThrowNotFound((menu.getId()));
        return menuRepository.save(menu);
    }

    @Override
    public void deleteById(String id) {
//        Menu menu = findByIdOrThrowNotFound(id);
//        menuRepository.delete(menu);
        menuRepository.deleteById(id);
    }

    private Menu findByIdOrThrowNotFound(String id){
        return menuRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Menu not found"));
    }

    private MenuResponse mapToResponse(Menu menu) {
        return MenuResponse.builder()
                .menuId(menu.getId())
                .name(menu.getName())
                .price(menu.getPrice())
                .build();
    }


}
