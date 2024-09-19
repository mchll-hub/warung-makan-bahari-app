package com.enigmacamp.wmb.service.impl;

import com.enigmacamp.wmb.entity.MenuImage;
import com.enigmacamp.wmb.repository.MenuImageRepository;
import com.enigmacamp.wmb.service.MenuImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
public class MenuImageServiceImpl implements MenuImageService {
    private final MenuImageRepository menuImageRepository;
    //lokasi penyimpanan file di direktory
    private final Path directoryPath = Paths.get("./asset/images");

    @Override
    public MenuImage createFile(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File not found");
        try {
            //logic untuk menyimpan file di directory
            Files.createDirectories(directoryPath);
            //penamaan file
            String filename = String.format("%d, %s", System.currentTimeMillis(), multipartFile.getOriginalFilename());
            //lokasi file beserta path direktori
            Path filePath = directoryPath.resolve(filename);
            //penyimpanan
            Files.copy(multipartFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            //logic untuk menyimpan di database
            MenuImage menuImage = MenuImage.builder()
                    .name(filename)
                    .contentType(multipartFile.getContentType())
                    .size(multipartFile.getSize())
                    .path(filePath.toString())
                    .build();

            return menuImageRepository.saveAndFlush(menuImage);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public Resource findByPath(String path) {
        try {
            Path filePath = Paths.get(path);
            return new UrlResource(filePath.toUri());
        } catch (MalformedURLException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public void deleteFile(String path) {

    }
}
