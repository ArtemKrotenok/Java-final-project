package com.gmail.artemkrotenok.web.controller.config;

import com.gmail.artemkrotenok.service.impl.AppUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.mock;

@Configuration
public class TestConfig {

    @Bean
    public AppUserDetailsService userDetailsService() {
        return mock(AppUserDetailsService.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return mock(PasswordEncoder.class);
    }

}
