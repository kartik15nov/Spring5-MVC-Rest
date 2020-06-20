package com.baya.Spring5MVCRest.services;

import com.baya.Spring5MVCRest.api.v1.model.CustomerDTO;

import java.util.List;

public interface CustomerService {
    List<CustomerDTO> getAllCustomers();

    CustomerDTO getCustomerById(Long id);
}
