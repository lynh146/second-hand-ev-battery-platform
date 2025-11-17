package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/", "/home",
                    "/search",
                    "/listings/**",
                    "/api/public/**",
                    "/css/**", "/js/**", "/images/**", "/webjars/**",
                    "/login", "/register",
                    "/admin/login"
                ).permitAll()

                .requestMatchers("/member/**").authenticated()

                .anyRequest().permitAll()
            )
            .formLogin(form -> form
                .loginPage("/login")          
                .defaultSuccessUrl("/", true)
                .permitAll()
            )

            .logout(logout -> logout
                .logoutUrl("/logout")         
                .logoutSuccessUrl("/")        
                .permitAll()
            );

        return http.build();
    }


    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user@example.com")
                .password("123456")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}
