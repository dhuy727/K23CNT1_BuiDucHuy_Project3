package com.bdh.lesson05.controller;

import com.bdh.lesson05.entity.Info;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping
public class HomeController {

    // Trang chủ người dùng (layout user)
    @GetMapping({"/", "/home"})
    public String home(Model model) {
        model.addAttribute("title", "Trang chủ người dùng");
        return "home";       // /templates/home.html
    }

    // Trang giới thiệu
    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "Giới thiệu");
        return "about";      // /templates/about.html
    }

    // Trang liên hệ
    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("title", "Liên hệ");
        return "contact";    // /templates/contact.html
    }

    // Trang profile demo (thầy cho trong tài liệu)
    @GetMapping("/profile")
    public String profile(Model model) {
        List<Info> profile = new ArrayList<>();

        profile.add(new Info(
                "Devmaster Academy",
                "huy",
                "contact@devmaster.edu.vn",
                "https://devmaster.edu.vn"
        ));

        model.addAttribute("DevmasterProfile", profile);
        return "Profile";    // /templates/Profile.html
    }
}
