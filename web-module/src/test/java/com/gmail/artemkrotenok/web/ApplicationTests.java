package com.gmail.artemkrotenok.web;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(value = "classpath:application-integration.properties")
class ApplicationTests {

    @Test
    @Disabled
    void contextLoads() {
    }
}
