package com.baya.Spring5MVCRest.services;

import com.baya.Spring5MVCRest.api.v1.model.CategoryDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAllCategories();

    CategoryDTO getCategoryByName(String name);
}
