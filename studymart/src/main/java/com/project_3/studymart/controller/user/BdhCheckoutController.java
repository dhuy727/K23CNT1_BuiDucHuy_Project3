package com.project_3.studymart.controller.user;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project_3.studymart.dto.BdhCartItemRequest;
import com.project_3.studymart.dto.BdhCheckoutRequest;
import com.project_3.studymart.entity.BdhUser;
import com.project_3.studymart.repository.BdhPaymentMethodRepository;
import com.project_3.studymart.repository.BdhTransportMethodRepository;
import com.project_3.studymart.repository.BdhUserRepository;
import com.project_3.studymart.service.BdhCheckoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BdhCheckoutController {

    private final BdhPaymentMethodRepository paymentRepo;
    private final BdhTransportMethodRepository transportRepo;
    private final BdhUserRepository userRepo;
    private final BdhCheckoutService checkoutService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/checkout")
    public String checkout(Model model) {
        model.addAttribute("paymentMethods", paymentRepo.findAll());
        model.addAttribute("transportMethods", transportRepo.findAll());
        return "checkout";
    }

    @PostMapping("/checkout")
    public String checkoutSubmit(
            @ModelAttribute BdhCheckoutRequest req,
            @RequestParam("cartJson") String cartJson,
            Principal principal,
            RedirectAttributes ra
    ) {
        try {
            if (principal == null) {
                ra.addFlashAttribute("msg", "Vui lòng đăng nhập trước khi thanh toán!");
                return "redirect:/login";
            }

            if (cartJson == null || cartJson.isBlank() || "[]".equals(cartJson.trim())) {
                ra.addFlashAttribute("msg", "Giỏ hàng trống!");
                return "redirect:/cart";
            }

            String username = principal.getName();
            BdhUser user = userRepo.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found: " + username));

            List<BdhCartItemRequest> cartItems = objectMapper.readValue(
                    cartJson,
                    new TypeReference<List<BdhCartItemRequest>>() {}
            );

            checkoutService.createOrder(user, req, cartItems);

            ra.addFlashAttribute("justOrdered", true);
            return "redirect:/my-orders";

        } catch (Exception ex) {
            ra.addFlashAttribute("msg", "Đặt hàng thất bại: " + ex.getMessage());
            return "redirect:/checkout";
        }
    }

}
