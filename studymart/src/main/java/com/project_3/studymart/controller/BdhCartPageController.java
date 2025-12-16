package com.project_3.studymart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BdhCartPageController {

    @GetMapping("/cart")
    public String cart() {
        return "cart";
    }
}
