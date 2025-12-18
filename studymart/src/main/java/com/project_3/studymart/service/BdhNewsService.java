package com.project_3.studymart.service;

import com.project_3.studymart.entity.BdhNews;
import com.project_3.studymart.repository.BdhNewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BdhNewsService {

    private final BdhNewsRepository newsRepository;

    public List<BdhNews> getAll() {
        return newsRepository.findAll();
    }

    public List<BdhNews> getActiveNews() {
        return newsRepository.findAllByOrderByCreatedAtDesc();
    }

    public BdhNews getById(Long id) {
        return newsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("News not found"));
    }

    public BdhNews create(BdhNews news) {
        news.setId(null);
        return newsRepository.save(news);
    }

    public BdhNews update(Long id, BdhNews news) {
        BdhNews existing = getById(id);
        existing.setTitle(news.getTitle());
        existing.setSummary(news.getSummary());
        existing.setContent(news.getContent());
        existing.setImageUrl(news.getImageUrl());
        return newsRepository.save(existing);
    }

    public void delete(Long id) {
        newsRepository.deleteById(id);
    }
}
