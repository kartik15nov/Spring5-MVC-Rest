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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;

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

        when(vendorRepository.findAll()).thenReturn(vendorList);

        //when
        List<VendorDTO> vendorDTOS = vendorService.getAllVendors();

        //then
        assertEquals(vendorList.size(), vendorDTOS.size());
        assertThat(vendorDTOS, hasItems(Matchers.hasProperty("vendorUrl")));
        verify(vendorRepository, times(1)).findAll();
    }

    @Test
    void getVendorById() {
        //given
        Vendor vendor = new Vendor();
        vendor.setId(5L);
        vendor.setName("Mike");

        when(vendorRepository.findById(anyLong())).thenReturn(java.util.Optional.of(vendor));

        //when
        VendorDTO vendorDTO = vendorService.getVendorById(5L);

        //then
        assertEquals(vendor.getName(), vendorDTO.getName());
        assertEquals("/api/v1/vendors/5", vendorDTO.getVendorUrl());
    }

    @Test
    void createNewVendor() {
        //given
        Vendor vendor = new Vendor();
        vendor.setId(5L);
        vendor.setName("Pri");

        when(vendorRepository.save(any())).thenReturn(vendor);

        VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);

        //when
        VendorDTO savedVendorDTO = vendorService.createNewVendor(vendorDTO);

        //then
        assertEquals(vendorDTO.getName(), savedVendorDTO.getName());
        assertEquals(VendorController.BASE_URL + "/" + 5, savedVendorDTO.getVendorUrl());
    }

    @Test
    void updateVendor() {
        //given
        Vendor vendor = new Vendor();
        vendor.setId(5L);
        vendor.setName("Joe");

        VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);

        when(vendorRepository.save(any())).thenReturn(vendor);

        //when
        VendorDTO updatedVendorDTO = vendorService.updateVendor(5L, vendorDTO);

        //then
        assertEquals(vendorDTO.getName(), updatedVendorDTO.getName());
        assertEquals(VendorController.BASE_URL + "/" + 5, updatedVendorDTO.getVendorUrl());
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

        when(vendorRepository.findById(anyLong())).thenReturn(java.util.Optional.of(vendor));
        when(vendorRepository.save(any())).thenReturn(savedVendor);

        //when
        VendorDTO patchedVendorDTO = vendorService.patchVendor(5L, vendorDTO);

        //then
        assertEquals(vendorDTO.getName(), patchedVendorDTO.getName());
        assertThat("Joe", not(equalTo(patchedVendorDTO.getName())));
        assertEquals(VendorController.BASE_URL + "/" + 5, patchedVendorDTO.getVendorUrl());

    }

    @Test
    void patchVendorVendorNotFound() {
        //given
        Vendor vendor = new Vendor();
        vendor.setId(5L);
        vendor.setName("Joe");

        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("Mike");

        when(vendorRepository.findById(anyLong())).thenReturn(Optional.empty());

        //when, then
        assertThrows(ResourceNotFoundException.class, () -> vendorService.patchVendor(5L, vendorDTO));
    }

    @Test
    void deleteVendor() {
        //when
        vendorService.deleteVendor(1L);

        //then
        verify(vendorRepository, times(1)).deleteById(anyLong());
    }
}