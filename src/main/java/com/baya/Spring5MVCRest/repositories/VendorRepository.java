package com.baya.Spring5MVCRest.repositories;

import com.baya.Spring5MVCRest.domain.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
}
