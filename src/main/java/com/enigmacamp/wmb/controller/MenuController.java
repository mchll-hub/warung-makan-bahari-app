package com.enigmacamp.wmb.controller;

import com.enigmacamp.wmb.dto.request.NewMenuRequest;
import com.enigmacamp.wmb.dto.response.CommonResponse;
import com.enigmacamp.wmb.dto.response.MenuResponse;
import com.enigmacamp.wmb.entity.Menu;
import com.enigmacamp.wmb.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/menus")
public class MenuController {
    private final MenuService menuService;


    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createMenu(@RequestBody NewMenuRequest request) {
        MenuResponse menuResponse = menuService.createNewMenu(request);
        CommonResponse<MenuResponse> response = CommonResponse.<MenuResponse>builder()
                .message("successfully create new menu")
                .statusCode(HttpStatus.CREATED.value())
                .data(menuResponse)
                .build();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    @GetMapping("/{id}")
    public Menu getMenuById (@PathVariable String id){
        return menuService.getById(id);
    }

//    @GetMapping
//    public List<Menu> getAllFilter(
//            @RequestParam (required = false) String name,
//            @RequestParam (required = false) Long minPrice,
//            @RequestParam (required = false) Long maxPrice){
//        return menuService.getAllFilter(name, minPrice, maxPrice);
//    }

    @GetMapping
    public List<Menu> getAllMenu(){
        return menuService.getAll();
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
