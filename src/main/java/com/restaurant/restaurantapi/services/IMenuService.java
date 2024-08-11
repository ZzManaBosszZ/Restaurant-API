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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IMenuService implements MenuService {
    private final MenuRepository menuRepository;
    private final MenuMapper menuMapper;

    @Override
    public MenuDTO create(CreateMenu createMenu) {
        Menu menu = Menu.builder()
                .name(createMenu.getName())
                .description(createMenu.getDescription())
                .build();
        menu = menuRepository.save(menu);
        return menuMapper.toMenuDTO(menu);
    }

    @Override
    public MenuDTO update(EditMenu editMenu) {
        Optional<Menu> menuOptional = menuRepository.findById(editMenu.getId());
        if (!menuOptional.isPresent()) {
            // Handle not found scenario
            throw new AppException(ErrorCode.MENU_NOTFOUND);
        }

        Menu menu = menuOptional.get();
        menu.setName(editMenu.getName());
        menu.setDescription(editMenu.getDescription());
        menu = menuRepository.save(menu);
        return menuMapper.toMenuDTO(menu);
    }

    @Override
    public void delete(Long id) {
        menuRepository.deleteById(id);
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
