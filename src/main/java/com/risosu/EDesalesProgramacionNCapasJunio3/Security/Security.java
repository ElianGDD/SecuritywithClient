package com.risosu.EDesalesProgramacionNCapasJunio3.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class Security {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.GET, "/Presentacion/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/Presentacion/login").permitAll()
                .requestMatchers("/Presentacion/CargaMasiva").hasAnyAuthority("Admin", "Programador")
                .requestMatchers("/Presentacion").hasAnyAuthority("Admin", "Analista", "Programador")
                .requestMatchers("/Presentacion/**").authenticated()
                .anyRequest().permitAll()
                )
                .sessionManagement(session -> session
                .maximumSessions(1)
                )
                .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/Presentacion")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
