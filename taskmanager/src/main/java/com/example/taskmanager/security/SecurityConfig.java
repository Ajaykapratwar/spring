package com.example.taskmanager.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

/**
 * Configures HTTP security, CORS, sessions, authentication, and logout.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    /**
     * Builds the security filter chain for all HTTP requests.
     *
     * @param http Spring Security HTTP configuration
     * @return configured security filter chain
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {

        http.
                // to determine which url/client should be allowed access to our api and to determine which api methods are allowed
                cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOriginPatterns(List.of("*"));

                    config.setAllowedMethods(List.of("GET", "POST", "DELETE", "PUT", "OPTIONS"));
                    config.setAllowedHeaders(List.of("*"));
                    config.setAllowCredentials(true);
                    return config;
                })).csrf(AbstractHttpConfigurer::disable)

                // to creates a session for authenticated user
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))

                // to register the router we want to ignore / protect
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/api/auth/login", "/api/auth/register")
                                .permitAll()
                                .anyRequest().authenticated())

                // this is to handle unauthorized access. It throws an auth error when you are unauthorized
                .exceptionHandling(ex ->
                        ex.authenticationEntryPoint(((request, response, authException) ->
                                response.sendError(401, "Unauthorized"))))

                // this is to handle logout
                .logout(logout -> logout
                        .logoutUrl("/api/auth/logout")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessHandler(((request, response, authentication) ->
                                response.setStatus(200))));

        return http.build();
    }

    /**
     * Exposes Spring Security's authentication manager.
     *
     * @param configuration authentication configuration supplied by Spring
     * @return configured authentication manager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) {
        return configuration.getAuthenticationManager();
    }

    /**
     * Creates the password encoder used for user credentials.
     *
     * @return BCrypt password encoder
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
