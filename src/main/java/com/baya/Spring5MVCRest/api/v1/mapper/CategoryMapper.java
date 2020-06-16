package com.baya.Spring5MVCRest.api.v1.mapper;

import com.baya.Spring5MVCRest.api.v1.model.CategoryDTO;
import com.baya.Spring5MVCRest.domain.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryDTO categoryToCategoryDTO(Category category);
}
