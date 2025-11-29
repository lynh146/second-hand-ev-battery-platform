package com.example.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOriginPattern("*");
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        config.setAllowCredentials(true);   

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .authenticationProvider(authenticationProvider())
            .authorizeHttpRequests(auth -> auth

                // PUBLIC PAGE
                .requestMatchers(
                    "/", "/home", "/search",
                    "/listings/**",
                    "/css/**", "/js/**", "/images/**", "/webjars/**", "/uploads/**",
                    "/login", "/register", "/forgot_password", "/reset_password", "/reset-password",
                    "/ws/**",
                    "/compare/**"   
                
                ).permitAll()

                // PUBLIC API cho guest
                .requestMatchers(
                    "/api/public/**",
                    "/api/listings/**",
                    "/api/users/register",
                    "/api/users/login"
                ).permitAll()

                 // ===== PUBLIC API =====
                .requestMatchers(
                        "/auth/forgot",
                        "/auth/reset"
                ).permitAll()

                // API lấy thông tin user hiện tại: phải đăng nhập
                .requestMatchers("/api/users/me").authenticated()

                // MEMBER
                .requestMatchers("/member/**").hasRole("MEMBER")

                // ADMIN: chỉ cho ADMIN truy cập
                .requestMatchers("/admin/**").hasRole("ADMIN")

                // các request còn lại yêu cầu login
                .anyRequest().authenticated()
            )

            // LOGIN FORM
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .usernameParameter("email")
                .passwordParameter("password")

                .successHandler((request, response, authentication) -> {

                    var authorities = authentication.getAuthorities();
                    boolean isAdmin = authorities.stream()
                            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

                    if (isAdmin) {
                        response.sendRedirect("/admin/dashboard");
                    } else {
                        response.sendRedirect("/");
                    }
                })

                .failureUrl("/login?error=true")
                .permitAll()
            )

            // LOGOUT
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll()
            );

        return http.build();
    }
}
