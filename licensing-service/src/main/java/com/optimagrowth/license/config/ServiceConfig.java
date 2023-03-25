package com.optimagrowth.license.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "exemple")
@Data
public class ServiceConfig {
    private String property;
}
