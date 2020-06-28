package com.baya.Spring5MVCRest.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CustomerDTO {

    @ApiModelProperty(value = "First Name of Customer", required = true)
    private String firstName;

    @ApiModelProperty(value = "Last Name of Customer", required = true)
    private String lastName;

    @JsonProperty("customer_url")
    private String customerUrl;
}
