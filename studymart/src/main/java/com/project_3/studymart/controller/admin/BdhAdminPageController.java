package com.project_3.studymart.controller.admin;

import com.project_3.studymart.service.BdhNewsService;
import com.project_3.studymart.service.BdhOrderService;
import com.project_3.studymart.service.BdhProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class BdhAdminPageController {

    private final BdhProductService productService;
    private final BdhOrderService orderService;
    private final BdhNewsService newsService;

    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("totalProducts", productService.getAll().size());
        model.addAttribute("totalOrders", orderService.getAllOrders().size());
        model.addAttribute("totalNews", newsService.getAll().size());
        return "admin/index";
    }
}
