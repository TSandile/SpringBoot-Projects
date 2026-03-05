package com.example.SpringSecurity.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    // ==================== AUTHENTICATION ====================

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures the DaoAuthenticationProvider with UserDetailsService
     * This provider fetches user details from the database via MemberDetailsService
     * and validates credentials against stored passwords
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /**
     * Creates the AuthenticationManager bean
     * This is used to authenticate users during login
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }

    // ==================== AUTHORIZATION ====================

    /**
     * Configures HTTP security and authorization rules
     * - Disables CSRF (can be enabled for state-changing requests)
     * - Public endpoints: /api/v1/** (registration, login, health checks)
     * - Protected endpoints: /api/** (require authentication with USER role minimum)
     * - Admin endpoints: /admin/** (require ADMIN role)
     * - Static resources: /static/**, /public/** (permit all)
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                // Disable CSRF for REST API (enable for web forms)
                .csrf(csrf -> csrf.disable())
                
                // Authorization configuration
                .authorizeHttpRequests((auth) -> {
                    // Public endpoints - no authentication needed
                    auth.requestMatchers("/api/v1/auth/**").permitAll();
                    auth.requestMatchers("/api/v1/public/**").permitAll();
                    auth.requestMatchers("/health").permitAll();
                    
                    // Static resources
                    auth.requestMatchers("/static/**", "/public/**").permitAll();
                    
                    // Admin endpoints - require ADMIN role
                    auth.requestMatchers("/admin/**").hasRole("ADMIN");
                    
                    // User endpoints - require USER role or higher
                    auth.requestMatchers("/api/**").hasRole("USER");
                    
                    // All other requests require authentication
                    auth.anyRequest().authenticated();
                })
                
                // HTTP Basic authentication for API testing
                .httpBasic(Customizer.withDefaults())
                
                // Logout configuration
                .logout((logout) -> {
                    logout.logoutUrl("/logout");
                    logout.logoutSuccessUrl("/api/v1/auth/login");
                    logout.permitAll();
                });

        return http.build();
    }
}
