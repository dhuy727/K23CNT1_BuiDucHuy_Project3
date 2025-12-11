package com.project_3.studymart.service;

import com.project_3.studymart.entity.BdhTransportMethod;
import com.project_3.studymart.repository.BdhTransportMethodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BdhTransportMethodService {

    private final BdhTransportMethodRepository repo;

    public List<BdhTransportMethod> getAll() {
        return repo.findAll();
    }

    public List<BdhTransportMethod> getActive() {
        return repo.findByActiveTrue();
    }

    public BdhTransportMethod getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Transport method not found"));
    }

    public BdhTransportMethod create(BdhTransportMethod tm) {
        tm.setId(null);
        return repo.save(tm);
    }

    public BdhTransportMethod update(Long id, BdhTransportMethod req) {
        BdhTransportMethod tm = getById(id);
        tm.setCode(req.getCode());
        tm.setName(req.getName());
        tm.setPrice(req.getPrice());
        tm.setEstimatedDays(req.getEstimatedDays());
        tm.setActive(req.getActive());
        return repo.save(tm);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
