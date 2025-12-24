package com.project_3.studymart.config;

import com.project_3.studymart.repository.BdhUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalModelAdvice {

    private final BdhUserRepository userRepo;

    @ModelAttribute("isAdmin")
    public boolean isAdmin(Authentication auth) {
        if (auth == null || !auth.isAuthenticated()) return false;
        return auth.getAuthorities().stream()
                .anyMatch(a -> "ROLE_ADMIN".equals(a.getAuthority()));
    }
    @ModelAttribute("username")
    public String username(Authentication auth) {
        if (auth == null || !auth.isAuthenticated()) return null;
        return auth.getName();
    }

}
