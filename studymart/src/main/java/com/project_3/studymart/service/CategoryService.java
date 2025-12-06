package com.project_3.studymart.service;

import com.project_3.studymart.entity.Category;
import com.project_3.studymart.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repo;

    public List<Category> getAll() {
        return repo.findAll();
    }

    public Category getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public Category create(Category category) {
        category.setId(null);
        if (category.getActive() == null) {
            category.setActive(true);
        }
        return repo.save(category);
    }

    public Category update(Long id, Category category) {
        Category old = getById(id);
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

    public List<Category> searchByName(String keyword) {
        return repo.findByNameContainingIgnoreCase(keyword);
    }
}
