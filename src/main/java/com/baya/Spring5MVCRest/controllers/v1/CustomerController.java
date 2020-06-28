package com.baya.Spring5MVCRest.controllers.v1;

import com.baya.Spring5MVCRest.api.v1.model.CustomerDTO;
import com.baya.Spring5MVCRest.api.v1.model.CustomerListDTO;
import com.baya.Spring5MVCRest.services.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api(tags = "This is Customer Controller")
@RestController
@RequestMapping(CustomerController.BASE_URL)
public class CustomerController {

    public static final String BASE_URL = "/api/v1/customers";
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @ApiOperation(value = "This will get a list of customers", notes = "These are some notes about the API")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CustomerListDTO getAllCustomers() {
        return new CustomerListDTO(customerService.getAllCustomers());
    }

    @ApiOperation(value = "Get Customer by Id")
    @GetMapping("/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO getCustomerById(@PathVariable Long customerId) {
        return customerService.getCustomerById(customerId);
    }

    @ApiOperation(value = "Create a new Customer")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO createNewCustomer(@RequestBody CustomerDTO customerDTO) {
        return customerService.createNewCustomer(customerDTO);
    }

    @ApiOperation(value = "Update a customer")
    @PutMapping("/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO updateCustomer(@PathVariable Long customerId, @RequestBody CustomerDTO customerDTO) {
        return customerService.updateCustomer(customerId, customerDTO);
    }

    @ApiOperation(value = "Update a customer property")
    @PatchMapping("/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO patchCustomer(@PathVariable Long customerId, @RequestBody CustomerDTO customerDTO) {
        return customerService.patchCustomer(customerId, customerDTO);
    }

    @ApiOperation(value = "Delete customer by Id")
    @DeleteMapping("/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCustomer(@PathVariable Long customerId) {
        customerService.deleteCustomer(customerId);
    }
}
