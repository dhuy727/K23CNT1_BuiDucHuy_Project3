package com.project_3.studymart.service;

import com.project_3.studymart.entity.BdhPaymentMethod;
import com.project_3.studymart.repository.BdhPaymentMethodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BdhPaymentMethodService {
    private final BdhPaymentMethodRepository repo;

    public List<BdhPaymentMethod> getActive() { return repo.findByActiveTrue(); }

    public BdhPaymentMethod getById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Payment method not found"));
    }

    public BdhPaymentMethod save(BdhPaymentMethod pm) { return repo.save(pm); }
}

