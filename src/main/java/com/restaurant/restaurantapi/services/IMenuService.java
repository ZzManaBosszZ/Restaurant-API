package com.restaurant.restaurantapi.services;

import com.restaurant.restaurantapi.dtos.menu.MenuDTO;
import com.restaurant.restaurantapi.entities.Menu;
import com.restaurant.restaurantapi.entities.User;
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

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class IMenuService implements MenuService {
    private final MenuRepository menuRepository;
    private final MenuMapper menuMapper;
    private final StorageService storageService;

    @Override
    public MenuDTO create(CreateMenu createMenu,  User user) {
        String generatedFileName = storageService.storeFile(createMenu.getImage());
        Menu menu = Menu.builder()
                .name(createMenu.getName())
                .image("http://localhost:8080/api/v1/FileUpload/files/" + generatedFileName)
                .description(createMenu.getDescription())

                .createdBy(user.getUsername())
                .menuFoods(new ArrayList<>())
                .createdDate(new Timestamp(System.currentTimeMillis()))
                .modifiedBy(user.getUsername())
                .modifiedDate(new Timestamp(System.currentTimeMillis()))
                .build();
        menu = menuRepository.save(menu);
        return menuMapper.toMenuDTO(menu);
    }

    @Override
    public MenuDTO update(EditMenu editMenu, User user) throws AppException {
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
        menu.setModifiedBy(user.getUsername());
        menu.setModifiedDate(new Timestamp(System.currentTimeMillis()));
        menu = menuRepository.save(menu);
        return menuMapper.toMenuDTO(menu);
    }


    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            if (menuRepository.existsById(id)) {
                menuRepository.deleteById(id)
                ;
            } else {
                throw new AppException(ErrorCode.FOOD_NOTFOUND);
            }
        }
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