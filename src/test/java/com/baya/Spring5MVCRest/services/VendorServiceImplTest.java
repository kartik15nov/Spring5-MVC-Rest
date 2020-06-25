package com.baya.Spring5MVCRest.services;

import com.baya.Spring5MVCRest.api.v1.mapper.VendorMapper;
import com.baya.Spring5MVCRest.api.v1.model.VendorDTO;
import com.baya.Spring5MVCRest.controllers.v1.VendorController;
import com.baya.Spring5MVCRest.domain.Vendor;
import com.baya.Spring5MVCRest.exceptions.ResourceNotFoundException;
import com.baya.Spring5MVCRest.repositories.VendorRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.any;

class VendorServiceImplTest {

    @Mock
    VendorRepository vendorRepository;

    VendorMapper vendorMapper = VendorMapper.INSTANCE;

    VendorService vendorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        vendorService = new VendorServiceImpl(vendorMapper, vendorRepository);
    }

    @Test
    void getAllVendors() {
        //given
        List<Vendor> vendorList = Arrays.asList(new Vendor(), new Vendor(), new Vendor());
        given(vendorRepository.findAll()).willReturn(vendorList);

        //when
        List<VendorDTO> vendorDTOS = vendorService.getAllVendors();

        //then
        then(vendorRepository)
                .should()
                .findAll();

        assertThat(vendorList.size(), is(equalTo(vendorDTOS.size())));
        assertThat(vendorDTOS, hasItems(Matchers.hasProperty("vendorUrl")));
    }

    @Test
    void getVendorById() {
        //given
        Vendor vendor = new Vendor();
        vendor.setId(5L);
        vendor.setName("Mike");

        given(vendorRepository.findById(anyLong())).willReturn(java.util.Optional.of(vendor));

        //when
        VendorDTO vendorDTO = vendorService.getVendorById(5L);

        //then
        then(vendorRepository)
                .should()
                .findById(anyLong());

        assertThat(vendor.getName(), is(equalTo(vendorDTO.getName())));
        assertThat("/api/v1/vendors/5", is(equalTo(vendorDTO.getVendorUrl())));
    }

    @Test
    void createNewVendor() {
        //given
        Vendor vendor = new Vendor();
        vendor.setId(5L);
        vendor.setName("Pri");

        given(vendorRepository.save(any())).willReturn(vendor);

        VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);

        //when
        VendorDTO savedVendorDTO = vendorService.createNewVendor(vendorDTO);

        //then
        then(vendorRepository)
                .should()
                .save(any());

        assertThat(vendorDTO.getName(), is(equalTo(savedVendorDTO.getName())));
        assertThat(VendorController.BASE_URL + "/" + 5, is(equalTo(savedVendorDTO.getVendorUrl())));
    }

    @Test
    void updateVendor() {
        //given
        Vendor vendor = new Vendor();
        vendor.setId(5L);
        vendor.setName("Joe");

        VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);

        given(vendorRepository.save(vendor)).willReturn(vendor);

        //when
        VendorDTO updatedVendorDTO = vendorService.updateVendor(5L, vendorDTO);

        //then
        then(vendorRepository)
                .should()
                .save(vendor);

        assertThat(vendorDTO.getName(), is(equalTo(updatedVendorDTO.getName())));
        assertThat(VendorController.BASE_URL + "/" + 5, is(equalTo(updatedVendorDTO.getVendorUrl())));
    }

    @Test
    void patchVendor() {
        //given
        Vendor vendor = new Vendor();
        vendor.setId(5L);
        vendor.setName("Joe");

        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("Mike");

        Vendor savedVendor = vendorMapper.vendorDTOToVendor(vendorDTO);
        savedVendor.setId(vendor.getId());

        given(vendorRepository.findById(anyLong())).willReturn(java.util.Optional.of(vendor));
        given(vendorRepository.save(any())).willReturn(savedVendor);

        //when
        VendorDTO patchedVendorDTO = vendorService.patchVendor(5L, vendorDTO);

        //then
        then(vendorRepository)
                .should()
                .findById(5L);

        then(vendorRepository)
                .should()
                .save(any());

        assertThat(vendorDTO.getName(), is(equalTo(patchedVendorDTO.getName())));
        assertThat("Joe", not(equalTo(patchedVendorDTO.getName())));
        assertThat(VendorController.BASE_URL + "/" + 5, is(equalTo(patchedVendorDTO.getVendorUrl())));

    }

    @Test
    void patchVendorVendorNotFound() {
        //given
        Vendor vendor = new Vendor();
        vendor.setId(5L);
        vendor.setName("Joe");

        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("Mike");

        given(vendorRepository.findById(anyLong())).willReturn(Optional.empty());

        //when, then
        willThrow(new ResourceNotFoundException())
                .given(vendorRepository)
                .findById(anyLong());
        try {
            vendorService.patchVendor(5L, vendorDTO);
            fail("Should throw Exception");
        } catch (ResourceNotFoundException ignored) {
        }
    }

    @Test
    void deleteVendor() {
        //when
        vendorService.deleteVendor(1L);

        //then
        then(vendorRepository)
                .should()
                .deleteById(any());
    }
}