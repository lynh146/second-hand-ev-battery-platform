package com.example.ev_battery_platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.example")
@EntityScan(basePackages = "com.example")
@EnableJpaRepositories(basePackages = "com.example")
public class EvBatteryPlatformApplication {
  public static void main(String[] args) {
    SpringApplication.run(EvBatteryPlatformApplication.class, args);
  }
}
