package com.baya.Spring5MVCRest.controllers.v1;

import com.baya.Spring5MVCRest.api.v1.mapper.VendorMapper;
import com.baya.Spring5MVCRest.api.v1.model.VendorDTO;
import com.baya.Spring5MVCRest.domain.Vendor;
import com.baya.Spring5MVCRest.exceptions.ResourceNotFoundException;
import com.baya.Spring5MVCRest.services.VendorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static com.baya.Spring5MVCRest.controllers.v1.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VendorControllerTest {

    @Mock
    VendorService vendorService;

    @InjectMocks
    VendorController vendorController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(vendorController)
                .setControllerAdvice(RestResponseEntityExceptionHandler.class)
                .build();
    }

    @Test
    void getAllVendors() throws Exception {
        //given
        List<VendorDTO> vendorDTOList = Arrays.asList(new VendorDTO(), new VendorDTO());
        when(vendorService.getAllVendors()).thenReturn(vendorDTOList);

        //when, then
        mockMvc.perform(get(VendorController.BASE_URL).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(2)));
    }

    @Test
    void getVendorById() throws Exception {
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setVendorUrl(VendorController.BASE_URL + "/2");

        when(vendorService.getVendorById(anyLong())).thenReturn(vendorDTO);

        //when, then
        mockMvc.perform(get(VendorController.BASE_URL + "/2").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendorUrl", equalTo(VendorController.BASE_URL + "/2")));
    }

    @Test
    void createNewVendor() throws Exception {
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("Test");
        vendorDTO.setVendorUrl(VendorController.BASE_URL + "/5");

        when(vendorService.createNewVendor(vendorDTO)).thenReturn(vendorDTO);

        //when, then
        mockMvc.perform(
                post(VendorController.BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(vendorDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo("Test")))
                .andExpect(jsonPath("$.vendorUrl", equalTo(VendorController.BASE_URL + "/5")));
    }

    @Test
    void updateVendor() throws Exception {
        //given
        Vendor vendor = new Vendor();
        vendor.setId(5L);

        VendorDTO vendorDTO = VendorMapper.INSTANCE.vendorToVendorDTO(vendor);
        vendorDTO.setName("Nandi");
        vendorDTO.setVendorUrl(VendorController.BASE_URL + "/5");

        when(vendorService.updateVendor(5L, vendorDTO)).thenReturn(vendorDTO);

        //when, then
        mockMvc.perform(
                put(VendorController.BASE_URL + "/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(vendorDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Nandi")))
                .andExpect(jsonPath("$.vendorUrl", equalTo(VendorController.BASE_URL + "/5")));
    }

    @Test
    void patchVendor() throws Exception {
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("Kalia");
        vendorDTO.setVendorUrl(VendorController.BASE_URL + "/5");

        when(vendorService.patchVendor(anyLong(), any(VendorDTO.class))).thenReturn(vendorDTO);

        //when, then
        mockMvc.perform(
                patch(VendorController.BASE_URL + "/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(vendorDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Kalia")))
                .andExpect(jsonPath("$.vendorUrl", equalTo(VendorController.BASE_URL + "/5")));
    }

    @Test
    void deleteVendor() throws Exception {
        //when, then
        mockMvc.perform(delete(VendorController.BASE_URL + "/6")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(vendorService, times(1)).deleteVendor(anyLong());
    }

    @Test
    public void testNotFoundException() throws Exception {

        when(vendorService.getVendorById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(VendorController.BASE_URL + "/222")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}