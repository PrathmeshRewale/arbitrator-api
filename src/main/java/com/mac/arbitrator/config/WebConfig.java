package com.mac.arbitrator.config;

import com.mac.arbitrator.security.CustomAccessDeniedHandler;
import com.mac.arbitrator.security.CustomAuthenticationEntryPoint;
import com.mac.arbitrator.security.CustomAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

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
                            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                            .requestMatchers("/api/auth/**").permitAll()
                            .requestMatchers("/api/admission/create").permitAll()
                            .requestMatchers("/api/mediation/create").permitAll()
                            .requestMatchers("/api/city/mini").permitAll()
                            .requestMatchers("/api/city/mini/**").permitAll()
                            .requestMatchers("/api/country/mini").permitAll()
                            .requestMatchers("/api/country/mini/**").permitAll()
                            .requestMatchers("/api/state/mini").permitAll()
                            .requestMatchers("/api/state/mini/**").permitAll()
                            .requestMatchers("/api/jurisdiction/mini").permitAll()
                            .requestMatchers("/api/jurisdiction/mini/**").permitAll()
                            .requestMatchers("/api/partytype/mini").permitAll()
                            .requestMatchers("/api/partytype/mini/**").permitAll()
                            .requestMatchers("/api/payment/**").permitAll()

                            // ✅ SWAGGER CONFIG
                            .requestMatchers(
                                    "/swagger-ui/**",
                                    "/v3/api-docs/**",
                                    "/v3/api-docs.yaml",
                                    "/swagger-resources/**",
                                    "/webjars/**"
                            ).permitAll()
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
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:8080"));
        config.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
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
