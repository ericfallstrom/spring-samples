package com.efall.springdatasample;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "application")
public class SpringDataSampleProperties {

    private String domain = "localhost";
}
