package com.project_3.studymart.controller.user;

import com.project_3.studymart.entity.BdhNews;
import com.project_3.studymart.service.BdhNewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class BdhNewsClientController {

    private final BdhNewsService newsService;

    @GetMapping
    public ResponseEntity<List<BdhNews>> list() {
        return ResponseEntity.ok(newsService.getActiveNews());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BdhNews> detail(@PathVariable Long id) {
        BdhNews news = newsService.getById(id);
        return ResponseEntity.ok(news);
    }
}
