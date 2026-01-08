package com.sachet.userservice.security;

import com.sachet.userservice.service.JwtService;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class JwtSecurityConfig {

    private final AuthEntryPoint authEntryPoint;
    private final AuthTokenFilter authTokenFilter;
    private final AuthenticationSecurityManager securityManager;

    public JwtSecurityConfig(AuthEntryPoint authEntryPoint, AuthTokenFilter authTokenFilter, AuthenticationSecurityManager securityManager) {
        this.authEntryPoint = authEntryPoint;
        this.authTokenFilter = authTokenFilter;
        this.securityManager = securityManager;
    }

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http, HttpSession httpSession) throws Exception {
        http.authorizeHttpRequests(authoriseRequests -> authoriseRequests
                .requestMatchers("/user/login").permitAll()
                .requestMatchers("/user/signup").permitAll()
                .anyRequest().authenticated());
        http.sessionManagement(
                session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );
        http.exceptionHandling(ex -> ex.authenticationEntryPoint(authEntryPoint));
        http.cors(Customizer.withDefaults());
        http.csrf(AbstractHttpConfigurer::disable);
        http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);
        http.authenticationManager(securityManager);

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .cors(Customizer.withDefaults()) // Enable CORS
//                // ... other security configurations like disabling csrf for state-less APIs
//                .csrf(csrf -> csrf.disable());
//        return http.build();
//    }

}
