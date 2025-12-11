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

    public List<BdhPaymentMethod> getAll() {
        return repo.findAll();
    }

    public List<BdhPaymentMethod> getActive() {
        return repo.findByActiveTrue();
    }

    public BdhPaymentMethod getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment method not found"));
    }

    public BdhPaymentMethod create(BdhPaymentMethod pm) {
        pm.setId(null);
        return repo.save(pm);
    }

    public BdhPaymentMethod update(Long id, BdhPaymentMethod req) {
        BdhPaymentMethod pm = getById(id);
        pm.setCode(req.getCode());
        pm.setName(req.getName());
        pm.setDescription(req.getDescription());
        pm.setActive(req.getActive());
        return repo.save(pm);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}

