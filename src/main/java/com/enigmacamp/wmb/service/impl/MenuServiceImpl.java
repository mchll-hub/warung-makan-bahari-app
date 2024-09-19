package com.enigmacamp.wmb.service.impl;

import com.enigmacamp.wmb.dto.request.NewMenuRequest;
import com.enigmacamp.wmb.dto.request.UpdateMenuRequest;
import com.enigmacamp.wmb.dto.response.FileResponse;
import com.enigmacamp.wmb.dto.response.MenuResponse;
import com.enigmacamp.wmb.entity.Menu;
import com.enigmacamp.wmb.entity.MenuImage;
import com.enigmacamp.wmb.repository.MenuRepository;
import com.enigmacamp.wmb.service.MenuImageService;
import com.enigmacamp.wmb.service.MenuService;
import com.enigmacamp.wmb.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final ValidationUtil validationUtil;
    private final MenuImageService menuImageService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public MenuResponse createNewMenu(NewMenuRequest request) {
        validationUtil.validate(request);
        //upload file
        MenuImage menuImage = menuImageService.createFile(request.getMultipartFile());

        //create menu
        Menu menu = Menu.builder()
                .name(request.getName())
                .price(request.getPrice())
                .menuImage(menuImage)
                .build();
        menuRepository.saveAndFlush(menu);
        return mapToResponse(menu);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<MenuResponse> createBulk(List<NewMenuRequest> requests) {
        List<Menu> menus = requests.stream().map(request -> Menu.builder()
                .name(request.getName())
                .price(request.getPrice())
                .build()).collect(Collectors.toList());
        menuRepository.saveAllAndFlush(menus);
        return menus.stream().map(menu -> mapToResponse(menu)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public Menu getById(String id) {
        return findByIdOrThrowNotFound(id);
    }

    @Transactional(readOnly = true)
    @Override
    public MenuResponse getOne(String id) {
        Menu menu = findByIdOrThrowNotFound(id);
        return mapToResponse(menu);
    }

    @Transactional(readOnly = true)
    @Override
    public Resource getMenuImageById(String id) {
        Menu menu = findByIdOrThrowNotFound(id);
        Resource resource = menuImageService.findByPath(menu.getMenuImage().getPath());
        return resource;
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public MenuResponse update(UpdateMenuRequest request) {
        validationUtil.validate(request);
        Menu menu = findByIdOrThrowNotFound(request.getId());
        menu.setName(request.getName());
        menu.setPrice(request.getPrice());
        menuRepository.saveAndFlush(menu);
        return mapToResponse(menu);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(String id) {
        Menu menu = findByIdOrThrowNotFound(id);
        menuRepository.delete(menu);
        menuRepository.deleteById(id);
    }

    private Menu findByIdOrThrowNotFound(String id){
        return menuRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Menu not found"));
    }

    private MenuResponse mapToResponse(Menu menu) {
        FileResponse fileResponse = FileResponse.builder()
                .fileName(menu.getMenuImage().getName())
                .url("api/v1/menus" +menu.getId()+ "/image")
                .build();
        return MenuResponse.builder()
                .menuId(menu.getId())
                .name(menu.getName())
                .price(menu.getPrice())
                .image(fileResponse)
                .build();
    }


}
