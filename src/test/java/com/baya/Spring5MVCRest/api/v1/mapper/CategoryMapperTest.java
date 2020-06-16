package com.baya.Spring5MVCRest.api.v1.mapper;

import com.baya.Spring5MVCRest.api.v1.model.CategoryDTO;
import com.baya.Spring5MVCRest.domain.Category;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryMapperTest {

    CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    @Test
    void categoryToCategoryDTO() {
        //given
        Category category = new Category();
        category.setName("Joe");
        category.setId(1L);

        //when
        CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);

        //then
        assertEquals(1L, categoryDTO.getId());
        assertEquals("Joe", categoryDTO.getName());
    }
}