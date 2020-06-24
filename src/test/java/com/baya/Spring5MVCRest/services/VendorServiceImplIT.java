package com.baya.Spring5MVCRest.services;

import com.baya.Spring5MVCRest.api.v1.mapper.VendorMapper;
import com.baya.Spring5MVCRest.api.v1.model.VendorDTO;
import com.baya.Spring5MVCRest.bootstrap.Bootstrap;
import com.baya.Spring5MVCRest.domain.Vendor;
import com.baya.Spring5MVCRest.repositories.CategoryRepository;
import com.baya.Spring5MVCRest.repositories.CustomerRepository;
import com.baya.Spring5MVCRest.repositories.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

@DataJpaTest
class VendorServiceImplIT {

    VendorMapper vendorMapper = VendorMapper.INSTANCE;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    VendorRepository vendorRepository;

    Bootstrap bootstrap;

    VendorService vendorService;

    @BeforeEach
    void setUp() {
        vendorService = new VendorServiceImpl(vendorMapper, vendorRepository);
        bootstrap = new Bootstrap(categoryRepository, customerRepository, vendorRepository);
        bootstrap.run();
    }

    @Test
    void patchVendorUpdateName() {
        //given
        Vendor vendor = vendorRepository.findAll().get(0);
        String vendorName = vendor.getName();

        VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
        vendorDTO.setName("Lopa");

        //when
        VendorDTO patchedVendorDTO = vendorService.patchVendor(vendor.getId(), vendorDTO);

        //then
        assertThat(vendorDTO.getName(), equalTo(patchedVendorDTO.getName()));
        assertThat(vendorName, not(equalTo(patchedVendorDTO.getName())));
    }
}