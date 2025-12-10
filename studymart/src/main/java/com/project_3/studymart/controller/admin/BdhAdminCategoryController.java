package com.project_3.studymart.controller.admin;

import com.project_3.studymart.entity.BdhCategory;
import com.project_3.studymart.service.BdhCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/categories")
@RequiredArgsConstructor
public class BdhAdminCategoryController {

    private final BdhCategoryService service;

    @GetMapping
    public List<BdhCategory> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public BdhCategory getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public BdhCategory create(@RequestBody BdhCategory category) {
        return service.create(category);
    }

    @PutMapping("/{id}")
    public BdhCategory update(@PathVariable Long id, @RequestBody BdhCategory category) {
        return service.update(id, category);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/search")
    public List<BdhCategory> search(@RequestParam String q) {
        return service.searchByName(q);
    }
}
