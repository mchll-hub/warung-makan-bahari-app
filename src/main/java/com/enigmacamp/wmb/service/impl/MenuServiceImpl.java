package com.enigmacamp.wmb.service.impl;

import com.enigmacamp.wmb.entity.Menu;
import com.enigmacamp.wmb.repository.MenuRepository;
import com.enigmacamp.wmb.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;

    @Override
    public Menu createNew(Menu menu) {
        return menuRepository.save(menu);
    }

    @Override
    public Menu getById(String id) {
        return findByIdOrThrowNotFound(id);
    }

    @Override
    public List<Menu> getAll(String name, Long minPrice, Long maxPrice) {
//        if (name == null){
//            return menuRepository.findAll();
//        }
        return menuRepository.findAllByNameLikeIgnoreCaseOrPriceBetween("%" +name+ "%", minPrice, maxPrice);
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
        return menuRepository.findById(id).orElseThrow(() -> new RuntimeException("Menu not found"));
    }
}
