package com.baya.Spring5MVCRest.bootstrap;

import com.baya.Spring5MVCRest.repositories.CategoryRepository;
import com.baya.Spring5MVCRest.repositories.CustomerRepository;
import com.baya.Spring5MVCRest.repositories.VendorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class BootstrapTestIT {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private VendorRepository vendorRepository;

    @Test
    void run() {
        assertEquals(5, categoryRepository.count());
        assertEquals(2, customerRepository.count());
        assertEquals(2, vendorRepository.count());
    }
}