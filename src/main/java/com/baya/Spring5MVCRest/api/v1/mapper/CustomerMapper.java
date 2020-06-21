package com.baya.Spring5MVCRest.api.v1.mapper;

import com.baya.Spring5MVCRest.api.v1.model.CustomerDTO;
import com.baya.Spring5MVCRest.domain.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    @Mapping(target = "customerUrl", ignore = true)
    CustomerDTO customerToCustomerDTO(Customer customer);

    @Mapping(target = "id", ignore = true)
    Customer customerDTOToCustomer(CustomerDTO customerDTO);
}
