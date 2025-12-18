package com.project_3.studymart.controller.user;

import com.project_3.studymart.entity.BdhTransportMethod;
import com.project_3.studymart.service.BdhTransportMethodService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/transport-methods")
@RequiredArgsConstructor
public class BdhTransportUserController {

    private final BdhTransportMethodService service;

    @GetMapping
    public List<BdhTransportMethod> getActive() {
        return service.getActive();
    }
}

