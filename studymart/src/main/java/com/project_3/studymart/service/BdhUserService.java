package com.project_3.studymart.service;

import com.project_3.studymart.dto.BdhRegisterRequest;
import com.project_3.studymart.entity.BdhUser;
import com.project_3.studymart.repository.BdhUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BdhUserService {

    private final BdhUserRepository repo;
    private final PasswordEncoder passwordEncoder;

    public BdhUser register(BdhRegisterRequest req) {
        if (repo.existsByUsername(req.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (repo.existsByEmail(req.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        BdhUser c = new BdhUser();
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

    public BdhUser getByUsername(String username) {
        return repo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
