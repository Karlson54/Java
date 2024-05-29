package com.example.finance.config.security;

import com.example.finance.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(authorizeRequests ->

                        authorizeRequests
                                .requestMatchers("/swagger-ui/**", "/swagger-resources/*", "/v3/api-docs/**",
                                        "/api/register/**")
                                .permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/register").permitAll() // Разрешаем всем доступ к /api/register
                                .requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll() // Разрешаем всем доступ к /api/auth
                                .requestMatchers(HttpMethod.POST, "/**").hasRole("TEACHER") // Разрешаем только пользователям с ролью ROLE_TEACHER для всех остальных POST запросов
                                .requestMatchers(HttpMethod.PUT, "/**").hasRole("TEACHER") // Разрешаем только пользователям с ролью ROLE_TEACHER для всех PUT запросов
                                .requestMatchers(HttpMethod.PATCH, "/**").hasRole("TEACHER") // Разрешаем только пользователям с ролью ROLE_TEACHER для всех PATCH запросов
                                .requestMatchers(HttpMethod.DELETE, "/**").hasRole("TEACHER") // Разрешаем только пользователям с ролью ROLE_TEACHER для всех DELETE запросов
                                .anyRequest().authenticated() // Все остальные запросы требуют аутентификации

                )
                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService.userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }
}
