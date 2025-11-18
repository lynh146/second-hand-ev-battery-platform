package com.example.Cofig;

import org.springframework.boot.context.properties.ConfigurationProperties;  
import org.springframework.context.annotation.Configuration;
import lombok.Data;

@Configuration
@Data
@ConfigurationProperties(prefix = "momo")  
public class MoMoConfig {

    private String partnerCode; 
    private String accessKey;
    private String secretKey;
    private String createPaymentUrl; 
    private String redirectUrl;
    private String ipnUrl;
    
    public static final String REQUEST_TYPE_ATM = "payWithATM"; 
}