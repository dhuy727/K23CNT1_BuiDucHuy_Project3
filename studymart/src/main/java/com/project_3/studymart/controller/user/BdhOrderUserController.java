package com.project_3.studymart.controller.user;

import com.project_3.studymart.repository.BdhOrderDetailRepository;
import com.project_3.studymart.repository.BdhOrderRepository;
import com.project_3.studymart.repository.BdhUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class BdhOrderUserController {

    private final BdhOrderRepository orderRepo;
    private final BdhOrderDetailRepository detailRepo;
    private final BdhUserRepository userRepo;

    @GetMapping("/my-orders")
    public String myOrders(Model model, Principal principal) {
        String username = principal.getName();
        var user = userRepo.findByUsername(username).orElseThrow();

        var orders = orderRepo.findByUserIdOrderByOrderDateDesc(user.getId());
        model.addAttribute("orders", orders);
        return "my-orders"; // đúng theo cây thư mục bạn đang để templates/
    }

    @GetMapping("/my-orders/{id}")
    public String myOrderDetail(@PathVariable Long id, Model model, Principal principal) {
        String username = principal.getName();
        var user = userRepo.findByUsername(username).orElseThrow();

        var order = orderRepo.findById(id).orElse(null);
        if (order == null || !order.getUser().getId().equals(user.getId())) {
            return "redirect:/my-orders";
        }

        model.addAttribute("order", order);
        model.addAttribute("details", detailRepo.findByOrderId(id));
        return "my-order-detail"; // templates/
    }
}
