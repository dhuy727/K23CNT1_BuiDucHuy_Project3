package com.project_3.studymart.controller.user;

import com.project_3.studymart.service.BdhProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class BdhProductUserController {

    private final BdhProductService productService;

    @GetMapping
    public String list(@RequestParam(required = false) String q,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "12") int size,
                       Model model) {

        var p = productService.searchProducts(q, page, size);

        model.addAttribute("pageTitle", "Sản phẩm");
        model.addAttribute("q", q);
        model.addAttribute("p", p);
        return "products"; // templates/products.html
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        var prd = productService.getById(id);
        model.addAttribute("p", prd);
        model.addAttribute("pageTitle", prd.getName());
        return "product-detail";
    }
}
