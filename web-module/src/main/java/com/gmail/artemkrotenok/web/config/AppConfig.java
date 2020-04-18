package com.gmail.artemkrotenok.web.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
        "com.gmail.artemkrotenok.repository",
        "com.gmail.artemkrotenok.service",
        "com.gmail.artemkrotenok.web"})
public class AppConfig {
}
