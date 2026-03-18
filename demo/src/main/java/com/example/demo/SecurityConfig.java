package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.
            authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests.
                requestMatchers(new AntPathRequestMatcher("/**")).permitAll()).
            csrf((csrf) -> csrf.ignoringRequestMatchers(new AntPathRequestMatcher("h2/console/**"))).
            headers((headers) -> headers.addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)));

        return http.build();
    }

    // 현재 버전 스타일 : Spring Security7 마이그레이션에서는 AntPathRequestMatcher와 MvcRequestMatcher가 더 이상 지원되지 않음
    // @Bean
    // SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    //     http
    //         .authorizeHttpRequests(authorize -> authorize
    //             .requestMatchers("/**").permitAll()
    //         )
    //         .csrf(csrf -> csrf
    //             .ignoringRequestMatchers("/h2-console/**")
    //         )
    //         .headers(headers -> headers
    //             .frameOptions(frame -> frame.sameOrigin())
    //         );

    //     return http.build();
    // }
}
