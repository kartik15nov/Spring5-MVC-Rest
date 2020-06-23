package com.baya.Spring5MVCRest.controllers.v1;

import com.baya.Spring5MVCRest.api.v1.mapper.CustomerMapper;
import com.baya.Spring5MVCRest.api.v1.model.CustomerDTO;
import com.baya.Spring5MVCRest.domain.Customer;
import com.baya.Spring5MVCRest.exceptions.ResourceNotFoundException;
import com.baya.Spring5MVCRest.services.CustomerService;
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

class CustomerControllerTest {

    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).setControllerAdvice(RestResponseEntityExceptionHandler.class).build();
    }

    @Test
    void getAllCustomers() throws Exception {
        //given
        List<CustomerDTO> customerDTOList = Arrays.asList(new CustomerDTO(), new CustomerDTO());
        when(customerService.getAllCustomers()).thenReturn(customerDTOList);

        //when, then
        mockMvc.perform(get(CustomerController.BASE_URL).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));
    }

    @Test
    void getCustomerById() throws Exception {
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerUrl(CustomerController.BASE_URL + "/2");

        when(customerService.getCustomerById(anyLong())).thenReturn(customerDTO);

        //when, then
        mockMvc.perform(get(CustomerController.BASE_URL + "/2").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerUrl", equalTo(CustomerController.BASE_URL + "/2")));
    }

    @Test
    void createNewCustomer() throws Exception {
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("Test");
        customerDTO.setLastName("Case");
        customerDTO.setCustomerUrl(CustomerController.BASE_URL + "/5");

        when(customerService.createNewCustomer(customerDTO)).thenReturn(customerDTO);

        //when, then
        mockMvc.perform(
                post(CustomerController.BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(customerDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", equalTo("Test")))
                .andExpect(jsonPath("$.lastName", equalTo("Case")))
                .andExpect(jsonPath("$.customerUrl", equalTo(CustomerController.BASE_URL + "/5")));
    }

    @Test
    void updateCustomer() throws Exception {
        //given
        Customer customer = new Customer();
        customer.setId(5L);

        CustomerDTO customerDTO = CustomerMapper.INSTANCE.customerToCustomerDTO(customer);
        customerDTO.setFirstName("Nandi");
        customerDTO.setLastName("Mallik");
        customerDTO.setCustomerUrl(CustomerController.BASE_URL + "/5");

        when(customerService.updateCustomer(5L, customerDTO)).thenReturn(customerDTO);

        //when, then
        mockMvc.perform(
                put(CustomerController.BASE_URL + "/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(customerDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo("Nandi")))
                .andExpect(jsonPath("$.lastName", equalTo("Mallik")))
                .andExpect(jsonPath("$.customerUrl", equalTo(CustomerController.BASE_URL + "/5")));
    }

    @Test
    void patchCustomer() throws Exception {
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("Kalia");
        customerDTO.setLastName("Sahu");
        customerDTO.setCustomerUrl(CustomerController.BASE_URL + "/5");

        when(customerService.patchCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(customerDTO);

        //when, then
        mockMvc.perform(
                patch(CustomerController.BASE_URL + "/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(customerDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo("Kalia")))
                .andExpect(jsonPath("$.lastName", equalTo("Sahu")))
                .andExpect(jsonPath("$.customerUrl", equalTo(CustomerController.BASE_URL + "/5")));
    }

    @Test
    void deleteCustomer() throws Exception {
        //when, then
        mockMvc.perform(delete(CustomerController.BASE_URL + "/6")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(customerService, times(1)).deleteCustomer(anyLong());
    }

    @Test
    public void testNotFoundException() throws Exception {

        when(customerService.getCustomerById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(CustomerController.BASE_URL + "/222")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}