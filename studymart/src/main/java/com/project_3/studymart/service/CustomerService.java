package com.project_3.studymart.service;

import com.project_3.studymart.dto.RegisterRequest;
import com.project_3.studymart.entity.Customer;
import com.project_3.studymart.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repo;
    private final PasswordEncoder passwordEncoder;

    public Customer register(RegisterRequest req) {
        if (repo.existsByUsername(req.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (repo.existsByEmail(req.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        Customer c = new Customer();
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

    public Customer getByUsername(String username) {
        return repo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
