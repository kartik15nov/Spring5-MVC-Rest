package com.baya.Spring5MVCRest.repositories;

import com.baya.Spring5MVCRest.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
