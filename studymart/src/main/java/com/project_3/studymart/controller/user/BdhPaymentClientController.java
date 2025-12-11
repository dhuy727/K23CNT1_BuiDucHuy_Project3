package com.project_3.studymart.controller.user;

import com.project_3.studymart.entity.BdhPaymentMethod;
import com.project_3.studymart.service.BdhPaymentMethodService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/payment-methods")
@RequiredArgsConstructor

public class BdhPaymentClientController {

    private final BdhPaymentMethodService service;

    @GetMapping
    public List<BdhPaymentMethod> getActive() {
        return service.getActive();
    }
}
