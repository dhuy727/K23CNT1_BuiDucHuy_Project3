package com.project_3.studymart.config;

import com.project_3.studymart.entity.BdhCustomer;
import com.project_3.studymart.repository.BdhCustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final BdhCustomerRepository customerRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Dùng Customer trong DB để Security login
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            BdhCustomer c = customerRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            return User.builder()
                    .username(c.getUsername())
                    .password(c.getPassword())
                    // .roles("USER") => nếu role="USER"
                    .roles(c.getRole()) // lưu "USER" hoặc "ADMIN"
                    .disabled(!Boolean.TRUE.equals(c.getActive()))
                    .build();
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/register").permitAll()  // cho phép đăng ký
                        .requestMatchers("/api/auth/me").authenticated()    // cần login
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")  // sau này
                        .anyRequest().permitAll()                           // tạm thời mở các API khác
                )
                .httpBasic(Customizer.withDefaults()); // login bằng Basic Auth

        return http.build();
    }
}
