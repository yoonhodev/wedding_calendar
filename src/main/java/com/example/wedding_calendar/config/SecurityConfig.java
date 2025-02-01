package com.example.wedding_calendar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.ignoringRequestMatchers("/api/public/**")) // 특정 API는 CSRF 검사 제외
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/customer").authenticated()  // 인증 필요
                        .requestMatchers("/api/public/**").permitAll() // 인증 없이 접근 가능
                        .anyRequest().authenticated()
                )
                .formLogin(withDefaults()); // 기본 로그인 설정 유지

        return http.build();
    }
}

