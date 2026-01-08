package com.mac.arbitrator.config;

import com.mac.arbitrator.security.CustomAccessDeniedHandler;
import com.mac.arbitrator.security.CustomAuthenticationEntryPoint;
import com.mac.arbitrator.security.CustomAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebConfig {
    private final CustomAuthenticationEntryPoint unauthorizedHandler;
    private final CustomAuthenticationFilter jwtAuthenticationFilter;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    public WebConfig(CustomAuthenticationEntryPoint unauthorizedHandler, CustomAuthenticationFilter jwtAuthenticationFilter, CustomAccessDeniedHandler accessDeniedHandler) {
        this.unauthorizedHandler = unauthorizedHandler;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) {
        try {
            httpSecurity
                    // Disable CSRF since we're using JWT
                    .csrf(csrf -> csrf.disable())
                    // No session will be created or used by Spring Security
                    .sessionManagement(session ->
                            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    )
                    // Handle unauthorized requests
                    .exceptionHandling(exception ->
                            exception.authenticationEntryPoint(unauthorizedHandler)
                                    .accessDeniedHandler(accessDeniedHandler)
                    )
                    // Configure endpoint access
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers("/api/auth/**").permitAll()
                            .requestMatchers("/api/admission/create").permitAll()
                            .requestMatchers("/api/mediation/create").permitAll()
                            .requestMatchers("/api/payment/create_link").permitAll()
                            .anyRequest().authenticated()
                    );

            // Add our custom JWT filter before the UsernamePasswordAuthenticationFilter
            httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

            return httpSecurity.build();

        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) {
        try {
            return authenticationConfiguration.getAuthenticationManager();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
