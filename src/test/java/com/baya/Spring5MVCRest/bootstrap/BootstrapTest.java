package com.baya.Spring5MVCRest.bootstrap;

import com.baya.Spring5MVCRest.repositories.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class BootstrapIntegrationTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void run() throws Exception {
        assertEquals(5, categoryRepository.count());
    }
}