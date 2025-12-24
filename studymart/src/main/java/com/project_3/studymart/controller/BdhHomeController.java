package com.project_3.studymart.controller;

import com.project_3.studymart.service.BdhNewsService;
import com.project_3.studymart.service.BdhProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class BdhHomeController {

    private final BdhProductService productService;
    private final BdhNewsService newsService;

    @GetMapping({"/", "/index"})
    public String index(Model model) {
        model.addAttribute("products", productService.getHotProducts(6));
        model.addAttribute("news", newsService.getActiveNews());
        return "index";
    }
}
