package com.restaurant.restaurantapi.services;

import com.restaurant.restaurantapi.dtos.menu.MenuDTO;
import com.restaurant.restaurantapi.entities.Menu;
import com.restaurant.restaurantapi.exceptions.AppException;
import com.restaurant.restaurantapi.exceptions.ErrorCode;
import com.restaurant.restaurantapi.mappers.MenuMapper;
import com.restaurant.restaurantapi.models.menu.CreateMenu;
import com.restaurant.restaurantapi.models.menu.EditMenu;
import com.restaurant.restaurantapi.repositories.MenuRepository;

import com.restaurant.restaurantapi.services.impl.MenuService;
import com.restaurant.restaurantapi.services.impl.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IMenuService implements MenuService {
    private final MenuRepository menuRepository;
    private final MenuMapper menuMapper;
    private final StorageService storageService;
    @Override
    public MenuDTO create(CreateMenu createMenu) {
        String generatedFileName = storageService.storeFile(createMenu.getImage());
        Menu menu = Menu.builder()
                .name(createMenu.getName())
                .image("http://localhost:8080/api/v1/FileUpload/files/" + generatedFileName)
                .description(createMenu.getDescription())
                .build();
        menu = menuRepository.save(menu);
        return menuMapper.toMenuDTO(menu);
    }

    @Override
    public MenuDTO update(EditMenu editMenu) {
        Menu menu = menuRepository.findById(editMenu.getId())
         .orElseThrow(() -> new AppException(ErrorCode.MENU_NOTFOUND));
        String imageUrl = menu.getImage();
        if (editMenu.getImage() != null && !editMenu.getImage().isEmpty()) {
            try {
                String generatedFileName = storageService.storeFile(editMenu.getImage());
                imageUrl = "http://localhost:8080/api/v1/FileUpload/files/" + generatedFileName;
            } catch (Exception e) {
                throw new AppException(ErrorCode.FILE_UPLOAD_FAILED);
            }
        }
        menu.setName(editMenu.getName());
        menu.setImage(imageUrl);
        menu.setDescription(editMenu.getDescription());
        menu = menuRepository.save(menu);
        return menuMapper.toMenuDTO(menu);
    }


    @Override
    public void delete(Long[] ids) {
        menuRepository.deleteAllById(List.of(ids));
    }

    @Override
    public MenuDTO findById(Long id) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.MENU_NOTFOUND));
        return menuMapper.toMenuDTO(menu);
    }

    @Override
    public List<MenuDTO> findAll() {
        List<Menu> menus = menuRepository.findAll();
        return menus.stream()
                .map(menuMapper::toMenuDTO)
                .toList();
    }
}
