package com.project_3.studymart.controller;

import com.project_3.studymart.dto.BdhRegisterRequest;
import com.project_3.studymart.entity.BdhCustomer;
import com.project_3.studymart.service.BdhCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class BdhAuthController {

    private final BdhCustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody BdhRegisterRequest request) {
        BdhCustomer c = customerService.register(request);
        c.setPassword(null); // không trả password mã hóa về
        return ResponseEntity.ok(c);
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(Authentication authentication) {
        String username = authentication.getName();
        BdhCustomer c = customerService.getByUsername(username);
        c.setPassword(null);
        return ResponseEntity.ok(c);
    }
}
