package com.project_3.studymart.controller.admin;

import com.project_3.studymart.entity.BdhOrder;
import com.project_3.studymart.enums.OrderStatus;
import com.project_3.studymart.repository.BdhOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BdhAdminDashboardController {

    private final BdhOrderRepository orderRepo;

    @GetMapping({"/admin", "/admin/dashboard"})
    public String dashboard(Model model) {
        long totalOrders = orderRepo.count();
        long pendingOrders = orderRepo.countByStatus(OrderStatus.PENDING);

        Double revenue = orderRepo.sumRevenue();
        if (revenue == null) revenue = 0.0;

        List<BdhOrder> latestOrders = orderRepo.findTop5ByOrderByOrderDateDesc();

        model.addAttribute("totalOrders", totalOrders);
        model.addAttribute("pendingOrders", pendingOrders);
        model.addAttribute("revenue", revenue);
        model.addAttribute("latestOrders", latestOrders);

        return "admin/dashboard";
    }

}
