package com.project_3.studymart.controller;

import com.project_3.studymart.service.BdhProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class BdhHomeController {

    private final BdhProductService productService;

    @GetMapping({"/", "/index"})
    public String index(Model model) {
        model.addAttribute("products", productService.getAll()); // hoặc getActiveProducts()
        return "index";
    }
}
