package com.example.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "momo")
@Getter
@Setter
public class MoMoConfig {
    private String endpoint;
    private String partnerCode;
    private String accessKey;
    private String secretKey;
    private String redirectUrl;
    private String notifyUrl;
}