package com.project_3.studymart.controller;

import com.project_3.studymart.dto.BdhRegisterRequest;
import com.project_3.studymart.service.BdhUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class BdhAuthPageController {

    private final BdhUserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<?> doRegister(@RequestBody BdhRegisterRequest req) {
        try {
            userService.register(req);
            return ResponseEntity.ok(
                    Map.of("message", "Đăng ký thành công")
            );
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", ex.getMessage())
            );
        }
    }
}
