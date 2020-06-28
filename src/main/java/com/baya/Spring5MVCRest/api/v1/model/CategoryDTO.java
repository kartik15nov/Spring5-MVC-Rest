package com.baya.Spring5MVCRest.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CategoryDTO {
    private Long id;

    @ApiModelProperty(value = "Category Name", required = true)
    private String name;
}
