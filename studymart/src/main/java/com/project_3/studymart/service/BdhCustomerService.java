package com.project_3.studymart.service;

import com.project_3.studymart.dto.BdhRegisterRequest;
import com.project_3.studymart.entity.BdhCustomer;
import com.project_3.studymart.repository.BdhCustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BdhCustomerService {

    private final BdhCustomerRepository repo;
    private final PasswordEncoder passwordEncoder;

    public BdhCustomer register(BdhRegisterRequest req) {
        if (repo.existsByUsername(req.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (repo.existsByEmail(req.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        BdhCustomer c = new BdhCustomer();
        c.setUsername(req.getUsername());
        c.setPassword(passwordEncoder.encode(req.getPassword()));
        c.setFullName(req.getFullName());
        c.setEmail(req.getEmail());
        c.setPhone(req.getPhone());
        c.setAddress(req.getAddress());
        c.setRole("USER");
        c.setActive(true);

        return repo.save(c);
    }

    public BdhCustomer getByUsername(String username) {
        return repo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
