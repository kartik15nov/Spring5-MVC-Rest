package com.baya.Spring5MVCRest.services;

import com.baya.Spring5MVCRest.api.v1.mapper.CustomerMapper;
import com.baya.Spring5MVCRest.api.v1.model.CustomerDTO;
import com.baya.Spring5MVCRest.domain.Customer;
import com.baya.Spring5MVCRest.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    @Mock
    CustomerRepository customerRepository;

    CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        customerService = new CustomerServiceImpl(CustomerMapper.INSTANCE, customerRepository);
    }

    @Test
    void getAllCustomers() {
        //given
        List<Customer> customerList = Arrays.asList(new Customer(), new Customer(), new Customer());
        when(customerRepository.findAll()).thenReturn(customerList);

        //when
        List<CustomerDTO> customerDTOS = customerService.getAllCustomers();

        //then
        assertEquals(3, customerDTOS.size());
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void getCustomerById() {
        //given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("Kartik");
        customer.setLastName("Mallik");

        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));

        //when
        CustomerDTO customerDTO = customerService.getCustomerById(1L);

        //then
        assertEquals(1L, customerDTO.getId());
        assertEquals("Kartik", customerDTO.getFirstName());
        assertEquals("Mallik", customerDTO.getLastName());
        assertEquals("/api/v1/customers/1", customerDTO.getCustomerUrl());
        verify(customerRepository, times(1)).findById(anyLong());
    }


    @Test
    void createNewCustomer() {
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(2L);
        customerDTO.setFirstName("Test");
        customerDTO.setLastName("Case");

        when(customerRepository.save(any())).thenReturn(CustomerMapper.INSTANCE.customerDTOToCustomer(customerDTO));

        //when
        CustomerDTO savedCustomerDTO = customerService.createNewCustomer(customerDTO);

        //then
        assertEquals(customerDTO.getId(), savedCustomerDTO.getId());
        assertEquals(customerDTO.getFirstName(), savedCustomerDTO.getFirstName());
        assertEquals(customerDTO.getLastName(), savedCustomerDTO.getLastName());
        assertEquals("/api/v1/customers/2", savedCustomerDTO.getCustomerUrl());
    }
}