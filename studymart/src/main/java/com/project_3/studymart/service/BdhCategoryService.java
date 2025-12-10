package com.project_3.studymart.service;

import com.project_3.studymart.entity.BdhCategory;
import com.project_3.studymart.repository.BdhCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BdhCategoryService {

    private final BdhCategoryRepository repo;

    public List<BdhCategory> getAll() {
        return repo.findAll();
    }

    public BdhCategory getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public BdhCategory create(BdhCategory category) {
        category.setId(null);
        if (category.getActive() == null) {
            category.setActive(true);
        }
        return repo.save(category);
    }

    public BdhCategory update(Long id, BdhCategory category) {
        BdhCategory old = getById(id);
        old.setName(category.getName());
        old.setDescription(category.getDescription());
        if (category.getActive() != null) {
            old.setActive(category.getActive());
        }
        return repo.save(old);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public List<BdhCategory> searchByName(String keyword) {
        return repo.findByNameContainingIgnoreCase(keyword);
    }
}
