package com.baya.Spring5MVCRest.bootstrap;

import com.baya.Spring5MVCRest.domain.Category;
import com.baya.Spring5MVCRest.domain.Customer;
import com.baya.Spring5MVCRest.domain.Vendor;
import com.baya.Spring5MVCRest.repositories.CategoryRepository;
import com.baya.Spring5MVCRest.repositories.CustomerRepository;
import com.baya.Spring5MVCRest.repositories.VendorRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class Bootstrap implements CommandLineRunner {
    private final CategoryRepository categoryRepository;
    private final CustomerRepository customerRepository;
    private final VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) {
        loadCategories();
        loadCustomers();
        loadVendors();
    }

    private void loadCustomers() {
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirstName("Michale");
        customer1.setLastName("Weston");

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setFirstName("Sam");
        customer2.setLastName("Axe");

        customerRepository.save(customer1);
        customerRepository.save(customer2);

        log.info("Customers loaded = {}", customerRepository.count());
    }

    private void loadCategories() {
        Category fruits = new Category();
        fruits.setName("Fruits");

        Category dried = new Category();
        dried.setName("Dried");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");

        categoryRepository.save(fruits);
        categoryRepository.save(dried);
        categoryRepository.save(fresh);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);

        log.info("Categories loaded = {}", categoryRepository.count());
    }

    private void loadVendors() {
        Vendor vendor1 = new Vendor();
        vendor1.setId(5L);
        vendor1.setName("Joe");

        Vendor vendor2 = new Vendor();
        vendor2.setId(6L);
        vendor2.setName("Mike");

        vendorRepository.save(vendor1);
        vendorRepository.save(vendor2);

        log.info("Vendors loaded = {}", vendorRepository.count());
    }
}
