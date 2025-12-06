package com.project_3.studymart.controller;

import com.project_3.studymart.dto.RegisterRequest;
import com.project_3.studymart.entity.Customer;
import com.project_3.studymart.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        Customer c = customerService.register(request);
        c.setPassword(null); // không trả password mã hóa về
        return ResponseEntity.ok(c);
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(Authentication authentication) {
        String username = authentication.getName();
        Customer c = customerService.getByUsername(username);
        c.setPassword(null);
        return ResponseEntity.ok(c);
    }
}
