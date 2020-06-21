package com.baya.Spring5MVCRest.services;

import com.baya.Spring5MVCRest.api.v1.mapper.CustomerMapper;
import com.baya.Spring5MVCRest.api.v1.model.CustomerDTO;
import com.baya.Spring5MVCRest.bootstrap.Bootstrap;
import com.baya.Spring5MVCRest.domain.Customer;
import com.baya.Spring5MVCRest.repositories.CategoryRepository;
import com.baya.Spring5MVCRest.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

@DataJpaTest
class CustomerServiceImplIT {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    Bootstrap bootstrap;

    CustomerService customerService;

    @BeforeEach
    void setUp() {
        customerService = new CustomerServiceImpl(CustomerMapper.INSTANCE, customerRepository);
        bootstrap = new Bootstrap(categoryRepository, customerRepository);
        bootstrap.run();
    }

    @Test
    public void patchCustomerUpdateFirstName() {
        //given
        Customer customer = customerRepository.findAll().get(0);
        String customerFirstName = customer.getFirstName();

        CustomerDTO customerDTO = CustomerMapper.INSTANCE.customerToCustomerDTO(customer);
        customerDTO.setFirstName("Lopa");

        //when
        CustomerDTO patchedCustomerDTO = customerService.patchCustomer(customer.getId(), customerDTO);

        //then
        assertThat(customerDTO.getFirstName(), equalTo(patchedCustomerDTO.getFirstName()));
        assertThat(customerFirstName, not(equalTo(patchedCustomerDTO.getFirstName())));
    }

    @Test
    public void patchCustomerUpdateLastName() {
        //given
        Customer customer = customerRepository.findAll().get(0);
        String customerLastName = customer.getLastName();

        CustomerDTO customerDTO = CustomerMapper.INSTANCE.customerToCustomerDTO(customer);
        customerDTO.setLastName("Muturi");

        //when
        CustomerDTO patchedCustomerDTO = customerService.patchCustomer(customer.getId(), customerDTO);

        //then
        assertThat(customerDTO.getFirstName(), equalTo(patchedCustomerDTO.getFirstName()));
        assertThat(customerLastName, not(equalTo(patchedCustomerDTO.getLastName())));
    }
}