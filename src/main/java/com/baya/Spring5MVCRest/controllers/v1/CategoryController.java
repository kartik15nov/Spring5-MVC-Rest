package com.baya.Spring5MVCRest.controllers.v1;

import com.baya.Spring5MVCRest.api.v1.model.CategoryDTO;
import com.baya.Spring5MVCRest.api.v1.model.CategoryListDTO;
import com.baya.Spring5MVCRest.services.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api(tags = "This is Category Controller")
@RestController
@RequestMapping(CategoryController.BASE_URL)
public class CategoryController {

    public static final String BASE_URL = "/api/v1/categories";
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @ApiOperation(value = "This will get a list of Categories", notes = "These are some notes about the API")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CategoryListDTO getAllCategories() {
        return new CategoryListDTO(categoryService.getAllCategories());
    }


    @ApiOperation(value = "Get Category by Name")
    @GetMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDTO getCategoryByName(@PathVariable String name) {
        return categoryService.getCategoryByName(name);
    }
}
