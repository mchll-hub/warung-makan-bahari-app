package com.enigmacamp.wmb.controller;

import com.enigmacamp.wmb.dto.request.NewMenuRequest;
import com.enigmacamp.wmb.dto.request.UpdateMenuRequest;
import com.enigmacamp.wmb.dto.response.CommonResponse;
import com.enigmacamp.wmb.dto.response.MenuResponse;
import com.enigmacamp.wmb.entity.Menu;
import com.enigmacamp.wmb.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/menus")
public class MenuController {
    private final MenuService menuService;


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createMenu(
            @RequestParam String name,
            @RequestParam Long price,
            @RequestParam MultipartFile image
    ) {
        NewMenuRequest request = NewMenuRequest.builder()
                .name(name)
                .price(price)
                .multipartFile(image)
                .build();
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

    @PostMapping("/bulk")
    public ResponseEntity<?> createBulkMenu(@RequestBody List<NewMenuRequest> menus) {
        List<MenuResponse> menuResponses = menuService.createBulk(menus);
        CommonResponse<List<MenuResponse>> response = CommonResponse.<List<MenuResponse>>builder()
                .message("successfully create new menus")
                .statusCode(HttpStatus.CREATED.value())
                .data(menuResponses)
                .build();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMenuById(@PathVariable String id) {
        MenuResponse menuResponse = menuService.getOne(id);
        CommonResponse<MenuResponse> response = CommonResponse.<MenuResponse>builder()
                .message("successfully get menu by id")
                .statusCode(HttpStatus.OK.value())
                .data(menuResponse)
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
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
    public ResponseEntity<?> updateMenu(@RequestBody UpdateMenuRequest request) {
        MenuResponse menuResponse = menuService.update(request);
        CommonResponse<MenuResponse> response = CommonResponse.<MenuResponse>builder()
                .message("successfully update menu")
                .statusCode(HttpStatus.OK.value())
                .data(menuResponse)
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMenuById(@PathVariable String id) {
        menuService.deleteById(id);
        CommonResponse<?> response = CommonResponse.builder()
                .message("successfully delete menu")
                .statusCode(HttpStatus.OK.value())
                .data("OK")
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
    //api/v1/menus/{id menu}/image
    @GetMapping("/{id}/image")
    public ResponseEntity<?> downloadMenuImage(@PathVariable String id) {
        Resource resource = menuService.getMenuImageById(id);
        //HTTP header response
        String headerValues = "attachment; filename=\"" + resource.getFilename() + "\"";
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION,headerValues)
                .body(resource);
    }

}
