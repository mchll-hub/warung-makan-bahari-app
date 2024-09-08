package com.enigmacamp.wmb.controller;

import com.enigmacamp.wmb.entity.Menu;
import com.enigmacamp.wmb.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/menu")
public class MenuController {
    private final MenuService menuService;


    @PostMapping
    public Menu createNewMenu(@RequestBody Menu menu){
        return menuService.createNew(menu);

    }

    @GetMapping("/{id}")
    public Menu getMenuById (@PathVariable String id){
        return menuService.getById(id);
    }

    @GetMapping
    public List<Menu> getAllMenu(
            @RequestParam (required = false) String name,
            @RequestParam (required = false) Long minPrice,
            @RequestParam (required = false) Long maxPrice){
        return menuService.getAll(name, minPrice, maxPrice);
    }

    @PutMapping
    public Menu updateMenu(@RequestBody Menu menu){
        return menuService.update(menu);
    }

    @DeleteMapping("/{id}")
    public void deleteMenuById(@PathVariable String id){
        menuService.deleteById(id);
    }
}
