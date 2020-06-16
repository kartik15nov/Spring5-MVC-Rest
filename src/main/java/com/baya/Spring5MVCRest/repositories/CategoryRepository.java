package com.baya.Spring5MVCRest.repositories;

import com.baya.Spring5MVCRest.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
