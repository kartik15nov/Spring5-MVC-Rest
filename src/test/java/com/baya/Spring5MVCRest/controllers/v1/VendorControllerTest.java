package com.baya.Spring5MVCRest.controllers.v1;

import com.baya.Spring5MVCRest.api.v1.model.VendorDTO;
import com.baya.Spring5MVCRest.exceptions.ResourceNotFoundException;
import com.baya.Spring5MVCRest.repositories.VendorRepository;
import com.baya.Spring5MVCRest.services.VendorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static com.baya.Spring5MVCRest.controllers.v1.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VendorController.class)
class VendorControllerTest {

    @MockBean
    VendorService vendorService;

    @MockBean
    VendorRepository vendorRepository;

    @Autowired
    MockMvc mockMvc;

    VendorDTO vendorDTO_1;
    VendorDTO vendorDTO_2;

    @BeforeEach
    void setUp() {
        vendorDTO_1 = new VendorDTO("Vendor 1", VendorController.BASE_URL + "/1");
        vendorDTO_2 = new VendorDTO("Vendor 2", VendorController.BASE_URL + "/2");
    }

    @Test
    void getAllVendors() throws Exception {
        //given
        given(vendorService.getAllVendors()).willReturn(Arrays.asList(vendorDTO_1, vendorDTO_2));

        //when, then
        mockMvc.perform(get(VendorController.BASE_URL).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(2)));

        then(vendorService)
                .should()
                .getAllVendors();
    }

    @Test
    void getVendorById() throws Exception {
        //given
        given(vendorService.getVendorById(anyLong())).willReturn(vendorDTO_2);

        //when, then
        mockMvc.perform(get(VendorController.BASE_URL + "/2").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendor_url", equalTo(VendorController.BASE_URL + "/2")));

        then(vendorService)
                .should()
                .getVendorById(2L);
    }

    @Test
    void createNewVendor() throws Exception {
        //given
        given(vendorService.createNewVendor(any(VendorDTO.class))).willReturn(vendorDTO_1);

        //when, then
        mockMvc.perform(
                post(VendorController.BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(vendorDTO_1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo("Vendor 1")))
                .andExpect(jsonPath("$.vendor_url", equalTo(VendorController.BASE_URL + "/1")));

        then(vendorService)
                .should()
                .createNewVendor(any(VendorDTO.class));
    }

    @Test
    void updateVendor() throws Exception {
        //given
        given(vendorService.updateVendor(eq(1L), any(VendorDTO.class))).willReturn(vendorDTO_1);

        //when, then
        mockMvc.perform(
                put(VendorController.BASE_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(vendorDTO_1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Vendor 1")))
                .andExpect(jsonPath("$.vendor_url", equalTo(VendorController.BASE_URL + "/1")));

        then(vendorService)
                .should()
                .updateVendor(eq(1L), any(VendorDTO.class));
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
                .andExpect(jsonPath("$.vendor_url", equalTo(VendorController.BASE_URL + "/5")));
    }

    @Test
    void deleteVendor() throws Exception {
        //when, then
        mockMvc.perform(delete(VendorController.BASE_URL + "/6")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        then(vendorService)
                .should()
                .deleteVendor(anyLong());
    }

    @Test
    public void testNotFoundException() throws Exception {

        given(vendorRepository.findById(anyLong())).willReturn(Optional.empty());

        willThrow(new ResourceNotFoundException())
                .given(vendorService)
                .getVendorById(anyLong());

        try {
            mockMvc.perform(get(VendorController.BASE_URL + "/222")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        } catch (ResourceNotFoundException ignored) {
        }
    }

}