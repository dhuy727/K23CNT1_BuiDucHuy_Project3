package com.project_3.studymart.controller;

import com.project_3.studymart.dto.BdhRegisterRequest;
import com.project_3.studymart.entity.BdhUser;
import com.project_3.studymart.service.BdhUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class BdhAuthController {

    private final BdhUserService userService;

    @PostMapping("/register")
    public BdhUser register(@RequestBody BdhRegisterRequest req) {
        return userService.register(req);
    }
}
