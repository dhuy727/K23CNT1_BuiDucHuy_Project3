package com.project_3.studymart.controller.admin;

import com.project_3.studymart.entity.BdhNews;
import com.project_3.studymart.service.BdhNewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/news")
@RequiredArgsConstructor
public class BdhAdminNewsController {

    private final BdhNewsService newsService;

    @GetMapping
    public ResponseEntity<List<BdhNews>> getAll() {
        return ResponseEntity.ok(newsService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BdhNews> getById(@PathVariable Long id) {
        return ResponseEntity.ok(newsService.getById(id));
    }

    @PostMapping
    public ResponseEntity<BdhNews> create(@RequestBody BdhNews news) {
        return ResponseEntity.ok(newsService.create(news));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BdhNews> update(@PathVariable Long id, @RequestBody BdhNews news) {
        return ResponseEntity.ok(newsService.update(id, news));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        newsService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
