package com.restaurant.restaurantapi.mappers;

import com.restaurant.restaurantapi.dtos.category.CategoryDTO;
import com.restaurant.restaurantapi.entities.Category;
import com.restaurant.restaurantapi.exceptions.AppException;
import com.restaurant.restaurantapi.exceptions.ErrorCode;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public CategoryDTO toCategoryDTO(Category model){
        if (model == null) throw new AppException(ErrorCode.NOTFOUND);
        CategoryDTO categoryDTO = CategoryDTO.builder()
                .id(model.getId())
                .name(model.getName())
                .slug(model.getSlug())
                .createdBy(model.getCreatedBy())
                .createdDate(model.getCreatedDate())
                .modifiedBy(model.getModifiedBy())
                .modifiedDate(model.getModifiedDate())
                .build();
        return categoryDTO;
    }
}
