package com.project_3.studymart.controller.user;

import com.project_3.studymart.service.BdhNewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/news")
public class BdhNewsViewController {

    private final BdhNewsService newsService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("newsList", newsService.getActiveNews());
        return "news/index";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("news", newsService.getById(id));
        return "news/detail";
    }
}
